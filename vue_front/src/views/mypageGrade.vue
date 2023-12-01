<template>
    <GnbBar />
    <myPage_top />
    <div class="mypage_menu">
        <myPageSide />
    </div>
    <div class="right_box">
        <h1 class="qna_title_big">회원등급</h1>
        <button @click="receiveCoupon" class="insert_cou">쿠폰받기</button>
        <img src="../assets/img/grade_img.png" alt="grade_img" />
    </div>
    <!-- 플로팅-->
    <Floating />
    <!-- 스크롤 TOP -->
    <ScrollTop />
</template>

<script>
import axios from "axios";
import GnbBar from "../components/gnbBar.vue";
import myPage_top from "@/components/myPage_top_grade.vue";
import myPageSide from "@/components/myPageSide.vue";

import Floating from "../components/floating.vue";
import ScrollTop from "@/components/scrollTop.vue";

export default {
    components: { GnbBar, myPage_top, myPageSide, Floating, ScrollTop },

    methods: {
        receiveCoupon() {
            const email = localStorage.getItem("userID");

            axios
                .post(`mypage/updatecoupon/${email}`)
                .then((response) => {
                    const message = response.data.message;
                    const coupons = response.data.coupons;

                    alert(message);
                    console.log("받은 쿠폰: ", coupons);
                    window.location.reload();
                })
                .catch((error) => {
                    console.error("쿠폰 발급 중 에러 발생", error);
                });
        },
    },
};
</script>
