package space.bielsolososdev.rdl.domain.url.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import space.bielsolososdev.rdl.api.model.urlredirect.UrlRedirectResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlRedirectAsyncService {

    /**
     * É apenas um teste para o executor async. Ele coleta as métricas brutas. Depois tem a ne
     */
    @Async("asyncExecutor")
    public void processStatisticsForRedirect(UrlRedirectResponse urlRedirect, HttpServletRequest request) {
        MDC.put("slug", urlRedirect.slug());
        try {
            log.info("Processando estatísticas para o slug {}.", urlRedirect.slug());

            String clientIp = getClientIp(request);
            String userAgent = safeHeader(request.getHeader("User-Agent"));
            String referer = safeHeader(request.getHeader("Referer"));
            String acceptLanguage = safeHeader(request.getHeader("Accept-Language"));
            String acceptEncoding = safeHeader(request.getHeader("Accept-Encoding")); // gzip, deflate, br
            String connection = safeHeader(request.getHeader("Connection")); // keep-alive
            String host = safeHeader(request.getHeader("Host")); // seu-dominio.com

            log.info(
                    "Métricas coletadas para slug={} | clientIp={} | userAgent={} | referer={} | acceptLanguage={} | acceptEncoding={} | connection={} | host={}",
                    urlRedirect.slug(),
                    clientIp,
                    userAgent,
                    referer,
                    acceptLanguage,
                    acceptEncoding,
                    connection,
                    host
            );
        } catch (Exception ex) {
            log.error("Erro ao processar estatísticas para slug={}: {}", urlRedirect.slug(), ex.getMessage(), ex);
        } finally {
            // Garantir que o MDC não vaze para outras requisições/threads
            MDC.remove("slug");
        }
    }

    private String safeHeader(String header) {
        if (header == null) return "-";
        String trimmed = header.trim();
        int max = 512;
        if (trimmed.length() > max) {
            return trimmed.substring(0, max) + "...";
        }
        return trimmed;
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // Se vier múltiplos IPs (proxy chain), pega o primeiro
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        return ip;
    }
}
