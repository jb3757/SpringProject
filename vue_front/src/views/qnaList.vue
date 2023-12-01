<template>
    <div>
        <GnbBar />
        <myPage_top />
        <div class="mypage_menu">
            <myPageSide />
        </div>
        <div class="right_box">
            <h1 class="qna_title_big">1:1 문의</h1>
            <!-- 탭시작 -->
            <section class="tabWrap">
                <TabsWrapper>
                    <TabItem :title="`답변 전체 (${count1})`">
                        <a href="http://localhost:8080/qnaWrite">
                            <div class="qna-btn" v-if="!nodata">1:1 문의하기</div>
                        </a>

                        <div v-if="qnaAllList.length === 0">
                            <div id="nodata" class="nodata">작성한 문의글이 없습니다</div>
                        </div>
                        <qnaAll
                            v-if="!nodata && qnaAllList.length > 0"
                            :qnaAllList="qnaAllList"
                            :showQnaAllContent="showQnaAllContent"
                            :toggleContent1="toggleContent1"
                            :conOpenBotton="conOpenBotton"
                            :conCloseBotton="conCloseBotton"
                            :editMode="editMode"
                            :qnaDelFunction="qnaDel"
                            :getEditQnaId="getEditId"
                        ></qnaAll>
                    </TabItem>
                    <TabItem :title="`답변 대기 (${count2})`">
                        <a href="http://localhost:8080/qnaWrite">
                            <div class="qna-btn" v-if="!nodata">1:1 문의하기</div>
                        </a>

                        <div class="qna-all">
                            <div v-if="qnaWaitList.length === 0">
                                <div id="nodata" class="nodata">답변 대기중인 문의글이 없습니다</div>
                            </div>
                            <qnaWait
                                v-if="!nodata && qnaWaitList.length > 0"
                                :qnaWaitList="qnaWaitList"
                                :showQnaWaitContent="showQnaWaitContent"
                                :toggleContent2="toggleContent2"
                                :conOpenBotton="conOpenBotton"
                                :conCloseBotton="conCloseBotton"
                                :editMode="editMode"
                                :qnaDelFunction="qnaDel"
                            ></qnaWait>
                        </div>
                    </TabItem>
                    <TabItem :title="`답변 완료 (${count3})`">
                        <a href="http://localhost:8080/qnaWrite">
                            <div class="qna-btn" v-if="!nodata">1:1 문의하기</div>
                        </a>

                        <div class="qna-all">
                            <div v-if="qnaDoneList.length === 0">
                                <div id="nodata" class="nodata">답변 완료된 문의글이 없습니다</div>
                            </div>
                            <qnaDone
                                v-if="!nodata && qnaDoneList.length > 0"
                                :qnaDoneList="qnaDoneList"
                                :showQnaDoneContent="showQnaDoneContent"
                                :toggleContent3="toggleContent3"
                                :conOpenBotton="conOpenBotton"
                                :conCloseBotton="conCloseBotton"
                                :editMode="editMode"
                            ></qnaDone>
                        </div>
                    </TabItem>
                </TabsWrapper>
            </section>
        </div>
    </div>
    <!-- 플로팅-->
    <Floating />
    <!-- 스크롤 TOP -->
    <ScrollTop />

</template>
<script>
import axios from "axios";
import "@/assets/css/qnaList.css";

import GnbBar from "../components/gnbBar.vue";

import TabsWrapper from "../components/TabsWrapper.vue";
import TabItem from "../components/TabItem.vue";
import qnaAll from "../components/qnaAll.vue";
import qnaWait from "../components/qnaWait.vue";
import qnaDone from "../components/qnaDone.vue";
import myPageSide from "@/components/myPageSide.vue";
import myPage_top from "@/components/myPage_top.vue";
import Floating from "../components/floating.vue";
import ScrollTop from "@/components/scrollTop.vue";


export default {
    components: {
        GnbBar,
        TabsWrapper,
        TabItem,
        qnaAll,
        qnaWait,
        qnaDone,
        myPage_top,
        myPageSide, 
        Floating, 
        ScrollTop
    },
    name: "qnaList",
    data() {
        return {
            email: "",
            qna_id: "",
            editMode: false,
            currentTagName: "전체(11)",
            showQnaAllContent: [],
            showQnaWaitContent: [],
            showQnaDoneContent: [],
            conOpenBotton: require("../assets/img/qna-open.png"),
            conCloseBotton: require("../assets/img/qna-close.png"),
            nodata: false,
            qnaAllList: [],
            qnaWaitList: [],
            qnaDoneList: [],
            count1: sessionStorage.getItem("qnaCount"),
            count2: sessionStorage.getItem("qnaW"),
            count3: sessionStorage.getItem("qnaF"),
        };
    },
    created() {
        this.email = localStorage.getItem("userID");

        this.getQnaAllList();
        this.getQnaWaitList();
        this.getQnaDoneList();
    },
    methods: {
        async getQnaAllList() {
            try {
                const url = "/qna/qnaAll";
                const data = { email: this.email };

                const qnaAllList = (
                    await axios({
                        method: "post",
                        url,
                        data,
                    }).catch((error) => {
                        console.log(error);
                    })
                ).data;

                if (qnaAllList.length > 0) {
                    this.qnaAllList = qnaAllList;
                }
                console.log(this.qnaAllList);
            } catch (error) {
                console.log(error);
            }
        },
        async getQnaWaitList() {
            try {
                const url = "/qna/qnaWait";
                const data = { email: this.email };

                const qnaWaitList = (
                    await axios({
                        method: "post",
                        url,
                        data,
                    }).catch((error) => {
                        console.log(error);
                    })
                ).data;

                if (qnaWaitList.length > 0) {
                    this.qnaWaitList = qnaWaitList;
                }
                console.log(this.qnaWaitList);
            } catch (error) {
                console.log(error);
            }
        },
        async getQnaDoneList() {
            try {
                const url = "/qna/qnaDone";
                const data = { email: this.email };

                const qnaDoneList = (
                    await axios({
                        method: "post",
                        url,
                        data,
                    }).catch((error) => {
                        console.log(error);
                    })
                ).data;

                if (qnaDoneList.length > 0) {
                    this.qnaDoneList = qnaDoneList;
                }
                console.log(this.qnaDoneList);
            } catch (error) {
                console.log(error);
            }
        },
        qnaDel(qna_id) {
            axios
                .post("/qna/qnaDel", { QNA_ID: qna_id })
                .then((response) => {
                    window.location.reload();
                })
                .catch((error) => {
                    console.error("Error deleting Q&A", error);
                });
        },
        getEditId(qna_id) {
            this.$router.push({ name: "qnaEdit", params: { id: qna_id } });
        },
    },
};
</script>
