<script>
import axios from "axios";

export default {
    props: {
        showQnaAllContent: {
            type: Array,
            default: () => [],
        },
        toggleContent1: Function,
        qnaAllList: Array,
        conOpenBotton: String,
        conCloseBotton: String,
        qnaDelFunction: Function,
        getEditQnaId: Function,
    },
    methods: {
        async toggleContent1(index) {
            this.showQnaAllContent[index] = !this.showQnaAllContent[index];
            await this.$emit("update:showQnaAllContent", this.showQnaAllContent);
        },
        handleQnaDel(qna_id) {
            this.qnaDelFunction(qna_id);
        },
        getEditId(qna_id) {
            this.getEditQnaId(qna_id);
        },
    },
    /* computed: {
    truncatedContents() {
      const maxChars = 30;
      if (this.all.QNA_TITLE.length > maxChars) {
        return this.all.QNA_TITLE.substring(0, maxChars) + "...";
      } else {
        return this.all.QNA_TITLE;
      }
    },
  }, */
};
</script>

<template>
    <div class="qna-all">
        <div class="qna-list">
            <div class="qna-box" :key="i" v-for="(all, i) in qnaAllList">
                <!-- 대기중/완료 아이콘 -->
                <div v-if="all.QNA_REP === 0">
                    <img class="wait-icon" src="../assets/img/waiting.png" alt="waiting-img" />
                </div>
                <div v-else>
                    <img class="done-icon" src="../assets/img/done.png" alt="done-img" />
                </div>
                <!-- 문의 제목/날짜 -->

                <div class="qna-date">{{ all.QNA_DATE }}</div>
                <div class="qna_title_rep">
                    <div class="qna-title">{{ all.QNA_TITLE }}</div>
                    <div class="younju" v-if="showQnaAllContent[i]">
                        <div class="qna_con_btn">
                            <div class="qna-con">{{ all.QNA_CON }}</div>
                            <div class="qna_btn_set" v-if="all.QNA_REP === 0">
                                <router-link :to="'/qnaEdit/' + all.QNA_ID">
                                    <button class="qna_editbtn" @click="getEditId(all.QNA_ID)">수정</button>
                                </router-link>
                                <button class="qna_delbtn" @click="handleQnaDel(all.QNA_ID)">삭제</button>
                            </div>
                        </div>

                        <hr class="on_reply" v-if="all.QNA_REPLY" />
                        <div class="qna-reply">
                            <svg
                                v-if="all.QNA_REPLY"
                                class="reply_s"
                                xmlns="http://www.w3.org/2000/svg"
                                width="16"
                                height="17"
                                viewBox="0 0 16 17"
                                fill="none"
                            >
                                <line x1="0.5" x2="0.5" y2="16" stroke="black" />
                                <line x1="16" y1="16.5" x2="-4.37114e-08" y2="16.5" stroke="black" />
                            </svg>
                            <div class="qna_reply_text">{{ all.QNA_REPLY }}</div>
                        </div>
                    </div>
                </div>
                <!-- 버튼 토글-문의 내용/답변 -->
                <button @click="toggleContent1(i)" class="con-open_close">
                    <img :src="conOpenBotton" alt="con-open" />
                    <img class="con-close" :src="conCloseBotton" alt="con-close" />
                </button>
            </div>
        </div>
    </div>
</template>
