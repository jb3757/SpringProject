<script>
import axios from "axios";

export default {
    props: {
        showQnaDoneContent: {
            type: Array,
            default: () => [],
        },
        toggleContent3: Function,
        qnaDoneList: Array,
        conOpenBotton: String,
        conCloseBotton: String,
    },
    methods: {
        async toggleContent3(index) {
            this.showQnaDoneContent[index] = !this.showQnaDoneContent[index];
            await this.$emit("update:showQnaDoneContent", this.showQnaDoneContent);
        },
    },
};
</script>

<template>
    <div class="qna-list">
        <div class="qna-box" :key="i" v-for="(done, i) in qnaDoneList">
            <!-- 대기중 아이콘 -->
            <div>
                <img class="done-icon" src="../assets/img/done.png" alt="done-img" />
            </div>
            <!-- 문의 제목/날짜 -->
            <div class="qna-date">{{ done.QNA_DATE }}</div>
            <div class="qna_title_rep">
                <div class="qna-title">{{ done.QNA_TITLE }}</div>
                <div class="younju" v-if="showQnaDoneContent[i]">
                    <div class="qna_con_btn">
                        <div class="qna-con">{{ done.QNA_CON }}</div>
                    </div>

                    <hr class="on_reply" v-if="done.QNA_REPLY" />
                    <div class="qna-reply">
                        <svg
                            v-if="done.QNA_REPLY"
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
                        <div class="qna_reply_text">{{ done.QNA_REPLY }}</div>
                    </div>
                </div>
            </div>
            <!-- 버튼 토글-문의 내용/답변 -->
            <button @click="toggleContent3(i)" class="con-open_close">
                <img :src="conOpenBotton" alt="con-open" />
                <img class="con-close" :src="conCloseBotton" alt="con-close" />
            </button>
        </div>
    </div>
</template>
