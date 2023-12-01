<script>
import axios from 'axios';

export default {
  props: {
    showQnaWaitContent: {
      type: Array,
      default: () => []
    },
    toggleContent2: Function,
    qnaWaitList: Array,
    conOpenBotton: String,
    conCloseBotton: String,
    qnaDelFunction: Function,
  },
  methods: {
    async toggleContent2(index) {
      this.showQnaWaitContent[index] = !this.showQnaWaitContent[index];
      await this.$emit('update:showQnaWaitContent', this.showQnaWaitContent);
    },
    handleQnaDel(qna_id) {
      this.qnaDelFunction(qna_id);
    },
  }
  /* computed: {
    truncatedContents() {
      const maxChars = 30;
      if (this.wait.QNA_TITLE.length > maxChars) {
        return this.wait.QNA_TITLE.substring(0, maxChars) + "...";
      } else {
        return this.wait.QNA_TITLE;
      }
    },
  }, */
};
</script>

<template>
    <div class="qna-list">
      <div class="qna-box" :key="i" v-for="(wait, i) in qnaWaitList">
        <!-- 대기중 아이콘 -->
        <div>
          <img class="wait-icon" src="../assets/img/waiting.png" alt="waiting-img">
        </div>
        <!-- 문의 제목/날짜 -->     
          <div class="qna-date">{{ wait.QNA_DATE }}</div> 
          <div class="qna_title_rep">
          <div class="qna-title">{{ wait.QNA_TITLE }}</div>        
            <div class="younju" v-if="showQnaWaitContent[i]">
              <div class="qna_con_btn">
                <div class="qna-con">{{ wait.QNA_CON }}</div>
                <div class="qna_btn_set">
                    <router-link :to="'/qnaEdit/' + wait.QNA_ID">
                      <button class="qna_editbtn">수정</button>
                    </router-link>          
                    <button class="qna_delbtn" @click="handleQnaDel(wait.QNA_ID)">삭제</button>
                </div>
              </div>
            </div>                
          </div>     
            <!-- 버튼 토글-문의 내용/답변 -->        
            <button @click="toggleContent2(i)" class="con-open_close">
              <img :src="conOpenBotton" alt="con-open" />                
              <img class="con-close" :src="conCloseBotton" alt="con-close" />
            </button>  
      </div>
    </div>
 
  
</template>>