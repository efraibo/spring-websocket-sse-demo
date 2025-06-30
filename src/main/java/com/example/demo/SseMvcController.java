package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@CrossOrigin
@RestController
public class SseMvcController {

    @GetMapping("/sse/stream-mvc-alt")
    public void streamEventsMvcAlt(HttpServletResponse response) throws IOException {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        int count = 0;
        while (count < 10) { // envia 10 eventos como exemplo
            writer.write("data: Mensagem MVC Alt #" + count + "\n\n");
            writer.flush();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            count++;
        }
        writer.close();
    }
}

