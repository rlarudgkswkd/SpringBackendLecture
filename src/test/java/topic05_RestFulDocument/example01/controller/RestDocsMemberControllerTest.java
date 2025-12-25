package topic05_RestFulDocument.example01.controller;

import org.example.Head11_RestFulAPiDesignAndImplement.topic05_RestFulDocument.example01.controller.RestDocsMemberController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestDocsMemberController.class)
@AutoConfigureRestDocs
@ContextConfiguration(classes = org.example.Head11_RestFulAPiDesignAndImplement.topic05_RestFulDocument.example01.RestDocsApplication.class)
class RestDocsMemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getMember_docs() throws Exception {
        mockMvc.perform(get("/api/members/{id}", 1L))
                .andExpect(status().isOk())
                .andDo(document("member-get",
                        pathParameters(
                                parameterWithName("id").description("회원 ID")
                        ),
                        responseFields(
                                fieldWithPath("id").description("회원 ID"),
                                fieldWithPath("name").description("회원 이름"),
                                fieldWithPath("email").description("회원 이메일")
                        )
                ));
    }
}