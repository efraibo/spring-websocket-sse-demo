package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@CrossOrigin
@RestController
public class SseMvcController {

    @Autowired
    private PedidoService pedidoService;

    private final ConcurrentLinkedQueue<PrintWriter> writers = new ConcurrentLinkedQueue<>();

    @GetMapping("/sse/stream-mvc-alt")
    public void streamEventsMvcAlt(HttpServletResponse response) throws IOException {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Connection", "keep-alive");

        PrintWriter writer = response.getWriter();
        writers.add(writer);

        AtomicBoolean isActive = new AtomicBoolean(true);

        // Subscreve aos eventos de pedidos
        pedidoService.getEventStream()
                .doOnNext(mensagem -> {
                    if (isActive.get()) {
                        try {
                            writer.write("data: " + mensagem + "\n\n");
                            writer.flush();
                        } catch (Exception e) {
                            isActive.set(false);
                            writers.remove(writer);
                        }
                    }
                })
                .subscribe();

        // Keep-alive
        try {
            while (isActive.get()) {
                writer.write("data: Keep-alive\n\n");
                writer.flush();
                Thread.sleep(30000); // 30 segundos
            }
        } catch (InterruptedException e) {
            isActive.set(false);
        } finally {
            writers.remove(writer);
            writer.close();
        }
    }
}
