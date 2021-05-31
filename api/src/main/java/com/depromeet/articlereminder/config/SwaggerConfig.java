package com.depromeet.articlereminder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(swaggerInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.depromeet.articlereminder"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder()
                .title("Article Web API Documentation")
                .description("링줍 서버 API에 대한 연동 문서입니다\n" +
                        "============================================================\n" +
                        "<table>\n" +
                        "  <tr>\n" +
                        "    <th>status</th>\n" +
                        "    <th>comment</th>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>200</td>\n" +
                        "    <td>조회 성공 (로그인 성공, 데이터 조회 성공....)</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>201</td>\n" +
                        "    <td>등록 성공 (가입 성공, created에 대한 응답)</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>203</td>\n" +
                        "    <td>수정 성공</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>204</td>\n" +
                        "    <td>삭제 성공 (no Content에 대한 응답)</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>401</td>\n" +
                        "    <td>Access token Expired</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>402</td>\n" +
                        "    <td>조회 실패</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>403</td>\n" +
                        "    <td>수정 실패</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>404</td>\n" +
                        "    <td>삭제 실패</td>\n" +
                        "  </tr>\n" +
                        "</table>" +
                        "")
                .version("1.0")
                .build();
    }
}