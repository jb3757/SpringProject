<style src="../assets/css/qnaEdit.css"></style>
<template>
    <div>
        <GnbBar />

        <div class="mypage_menu">
            <myPageSide />
        </div>
        <div class="right_box">
            <h1 class="qna_title_big">1:1 문의 수정</h1>
            <div class="write_box">
                <div class="title_write">
                    <h2 class="title_text">제목</h2>
                    <input class="title_input" type="text" v-model="this.qna.qna_title" maxlength="20" />
                </div>
                <div class="con_write">
                    <h2 class="con_text">내용</h2>
                    <textarea class="con_input" type="text" v-model="this.qna.qna_con" maxlength="500" />
                </div>
            </div>
            <div class="btn_box">
                <button class="btn_cancel">취소</button>
                <button class="btn_accept" @click="qnaEditData">문의 수정</button>
            </div>
        </div>
    </div>

    <!-- 플로팅-->
    <Floating />
    <!-- 스크롤 TOP -->
    <ScrollTop />
</template>

<script>
import axios from "axios";

import GnbBar from "../components/gnbBar.vue";

export default {
    components: {
        GnbBar,
    },
    data() {
        return {
            qna: {
                email: "",
                qna_id: "",
                qna_title: "",
                qna_con: "",
                qna_rep: 0,
            },
            qna_id: "",
        };
    },
    created() {
        this.qna.email = localStorage.getItem("userID");
        this.qna.qna_id = this.$route.params.id; // 파라미터에서 아이디 받아오기
        this.getOriginalData();
    },
    methods: {
        getOriginalData() {
            axios({
                url: "/qna/qnaOriginalData",
                method: "post",
                data: { QNA_ID: this.qna.qna_id },
            }).then((res) => {
                this.qna.qna_title = res.data[0].QNA_TITLE;
                this.qna.qna_con = res.data[0].QNA_CON;
            });
        },
        async qnaEditData() {
            if (!this.qna.qna_title || !this.qna.qna_con) {
                alert("제목과 내용을 모두 입력해주세요.");
                return;
            }

            const editData = {
                qna_title: this.qna.qna_title,
                qna_con: this.qna.qna_con,
                qna_id: this.qna.qna_id,
            };
            axios({
                url: "/qna/qnaEdit",
                method: "post",
                data: editData,
            }).then((res) => {
                console.log(editData);
                if (res.data) {
                    alert("문의 내용이 수정되었습니다.");
                    console.log(editData);
                    this.$router.push({ path: "/qna" });
                    console.log("yeah");
                } else {
                    alert("문의 수정에 실패했습니다.\n다시 시도해주세요.");
                }
            });
            /* if(!this.qna.qna_title || !this.qna.qna_con) {
        alert("제목과 내용을 모두 입력해주세요.");
        return;
      }

      const editData = {
        qna_id: this.qna.qna_id,
        qna_title: this.qna.qna_title,
        qna_con: this.qna.qna_con
      }

      axios.post('/qna/qnaEdit', editData)
      .then((response) => {
        if (response.data.success) {
          alert('문의 내용이 수정되었습니다.');
          console.log(editData);
          this.$router.push({ path: '/qna' });
        } else {
          alert('문의 수정에 실패했습니다.\n다시 시도해주세요.');
        }
      })
      .catch((err) => {
        console.log(err);
      }); */
        },
        /* async qnaEdit() {
      const editData = {
        QNA_TITLE: this.qna_title,
        QNA_CON: this.qna_con,
        QNA_ID: this.qna_id
      }

      axios.post("/qna/qnaEdit", editData)
          .then(response => {
            window.location.reload();
          })
          .catch(error => {
            console.error("Error deleting Q&A", error);
          });
    }, */
    },
};
</script>
