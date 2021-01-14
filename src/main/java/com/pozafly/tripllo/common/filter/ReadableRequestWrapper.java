package com.pozafly.tripllo.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

// https://lannstark.tistory.com/19
// https://singun.github.io/2017/02/04/logging-requestbody/
// 이 클래스는 HTTP의 @RequestBody를 interceptor에서 사용하기 위해 사용.
@Slf4j
public class ReadableRequestWrapper extends HttpServletRequestWrapper {

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    private static final int EOF = -1;

    private byte[] bytes;
    private final Charset encoding;
    private String requestBody = null;

    public ReadableRequestWrapper(HttpServletRequest request) {
        super(request);

        String encoding = request.getCharacterEncoding();
        this.encoding = StringUtils.isEmpty(encoding) ? StandardCharsets.UTF_8 : Charset.forName(encoding);
        try {
            // InputStreaam을 열어 byte 배열에 rawData로 저
            InputStream in = super.getInputStream();
            bytes = toByteArray(in);
            this.requestBody = new String(bytes);
        } catch (IOException e) {
            log.error("ReaderRequestWrapper에서 Stream을 열다가 IOException 발생", e);
        }
    }

    public String getRequestBody() {
        return this.requestBody;
    }

    private static byte[] toByteArray(final InputStream input) throws IOException {
        try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            copyLarge(input, output, new byte[DEFAULT_BUFFER_SIZE]);
            return output.toByteArray();
        }
    }

    private static void copyLarge(final InputStream input, final OutputStream output, final byte[] buffer) throws IOException {
        int n;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
    }

    // 여기서부터는 @RequestBody를 한번 읽고 나면 inputStream이 닫히므로 controller에서 읽을 수 없게 된다. 그럴 때를 방지함.
    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.bytes);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream(), this.encoding));
    }
}
