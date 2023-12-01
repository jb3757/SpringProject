<template>
    <div>
        <GnbBar />
        <myPage_top />
        <div class="mypage_menu">
            <myPageSide />
        </div>
        <div class="right_box">
            <h1 class="qna_title_big">자주 묻는 질문 FAQ</h1>
            <!-- 탭시작 -->
            <section class="tabWrap">
                <TabsWrapper>
                    <TabItem title="faq" class="faq_title">
                        <div class="qna-all">
                            <!-- 여기부터1111111111111 -->
                            <div class="qna-list">
                                <div class="qna-box">
                                    <!-- 대기중 아이콘 -->
                                    <div>
                                        <img class="done-icon" src="../assets/img/b1.png" alt="done-img" />
                                    </div>
                                    <div class="qna_title_rep">
                                        <div class="qna-title">등급별 쿠폰은 어떻게 발급 받고, 어떻게 사용하나요?</div>
                                        <div class="younju" v-if="showFaqContent1">
                                            <div class="qna-reply">
                                                <div class="qna_reply_text">
                                                    등급별 쿠폰은 익월을 제외한 최근 6개월 구매한 금액의 총합으로 결정됩니다.<br /><br />

                                                    매월 1일 회원등급이 산정이 완료되면, 마이페이지 상단에서 확인하실 수 있습니다.<br /><br />

                                                    - 플래티넘 회원(30만원 이상 구매 고객) : 20% 할인 쿠폰과 무료배송 쿠폰 월 1장 제공<br />

                                                    - 골드 회원(20만원 이상 구매 고객) : 15% 할인 쿠폰과 무료배송 쿠폰 월 1장 제공<br />

                                                    - 실버 회원(10만원 이상 구매 고객) : 10% 할인 쿠폰 월 1장 제공<br />

                                                    - 프렌즈 회원(신규 가입 회원, 10만원 미만 구매 고객) : 5% 할인 쿠폰 월 1장 제공<br /><br />

                                                    ㅁ 5만원 이상 구매시는 자동으로 배송비 무료가 적용되며, 무료배송 쿠폰을 적용하실 필요가 없습니다.
                                                    <br /><br />
                                                    ㅁ 쿠폰은 온라인 주문 시 사용하실 수 있으며, 결제 시에'쿠폰사용하기’를 클릭한 후 할인쿠폰
                                                    선택창에서 각 쿠폰을 선택/적용하여 사용하시면 됩니다. <br /><br />

                                                    ㅁ 각 쿠폰은 장바구니별 1장 사용이 가능하며, 사용하여 주문완료된 쿠폰은 소멸됩니다. <br /><br />

                                                    ㅁ 무료배송 쿠폰은 기타 할인쿠폰과 중복사용 불가합니다.
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <button @click="btnToggle1" class="con-open_close">
                                        <img :src="conOpenBotton" alt="con-open" />
                                        <img class="con-close" :src="conCloseBotton" alt="con-close" />
                                    </button>
                                </div>
                                <!-- 여기까지 -->
                                <!-- 여기부터2222222222222 -->
                                <div class="qna-list">
                                    <div class="qna-box">
                                        <!-- 대기중 아이콘 -->
                                        <div>
                                            <img class="done-icon" src="../assets/img/b2.png" alt="done-img" />
                                        </div>
                                        <div class="qna_title_rep">
                                            <div class="qna-title">주문을 취소할 수 있나요?</div>
                                            <div class="younju" v-if="showFaqContent2">
                                                <div class="qna-reply">
                                                    <div class="qna_reply_text">
                                                        주문취소는 마이페이지 > 쇼핑내역 > 주문/배송목록에서 회원 로그인 후 하실 수 있습니다.
                                                        <br /><br />
                                                        배송준비 상태에서는 주문을 접수하여 주문취소가 가능하며 그외 배송상태에서는 주문 취소가
                                                        불가능합니다.<br /><br />
                                                        배송이 완료된 상태에서 환불이나 반품을 원할 경우 고객센터의 1:1 문의 접수 이용하여 주시면,
                                                        최대한 확인하여 답변드리겠습니다.
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <button @click="btnToggle2" class="con-open_close">
                                        <img :src="conOpenBotton" alt="con-open" />
                                        <img class="con-close" :src="conCloseBotton" alt="con-close" />
                                    </button>
                                </div>
                                <!-- 여기까지 -->
                                <!-- 여기부터3333333333 -->
                                <div class="qna-list">
                                    <div class="qna-box">
                                        <!-- 대기중 아이콘 -->
                                        <div>
                                            <img class="done-icon" src="../assets/img/b3.png" alt="done-img" />
                                        </div>
                                        <div class="qna_title_rep">
                                            <div class="qna-title">주문하면 얼마 만에 받아볼 수 있나요?</div>
                                            <div class="younju" v-if="showFaqContent3">
                                                <div class="qna-reply">
                                                    <div class="qna_reply_text">
                                                        고객님께서 주문하신 상품을 실제 받으시는 날은 "예상출고일 + 배송일"입니다. <br /><br />

                                                        '예상출고일'이란 근무일 기준으로 도서가 준비되는 시간 만을 안내하는 것이며, 배송시간은
                                                        예상출고일 외 택배를 통해 고객님께 실제 배달되는 기간을 말합니다. <br /><br />

                                                        '출고예정일'은 상품에 따라 준비기간이 다르며 배송 시간은 지역에 따라 약간씩 다르나 보통
                                                        1~3일정도 소요됩니다. (도서산간지역은 최대 7일 소요) <br /><br />

                                                        ㅁ 예상출고일은 영업일 기준으로 산정되며, 일요일과 공휴일 및 기타 휴무일에는 배송되지
                                                        않습니다. (우체국 배송은 토요일 휴무로 배송 불가함) <br /><br />

                                                        ㅁ 주문한 상품의 당사에 재고가 없을 때는 거래처로 다시 주문 요청한 후 출고되므로 3∼7일내외
                                                        정도 더 소요됩니다. <br /><br />

                                                        ㅁ 천재지변 등의 불가항력적 사유로 인한 배송 지연은 그 해당기간 동안의 배송소요 기간에서
                                                        제외됩니다.
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <button @click="btnToggle3" class="con-open_close">
                                        <img :src="conOpenBotton" alt="con-open" />
                                        <img class="con-close" :src="conCloseBotton" alt="con-close" />
                                    </button>
                                </div>
                                <!-- 여기까지 -->
                                <!-- 여기부터444444444444 -->
                                <div class="qna-list">
                                    <div class="qna-box">
                                        <!-- 대기중 아이콘 -->
                                        <div>
                                            <img class="done-icon" src="../assets/img/b4.png" alt="done-img" />
                                        </div>
                                        <div class="qna_title_rep">
                                            <div class="qna-title">주문취소를 하면 적용한 쿠폰이 없어지나요?</div>
                                            <div class="younju" v-if="showFaqContent4">
                                                <div class="qna-reply">
                                                    <div class="qna_reply_text">
                                                        무료배송 쿠폰을 사용하여 결제완료를 한 경우 적용된 쿠폰은 소멸됩니다. <br /><br />

                                                        따라서 주문취소/반품을 하셔도 적용된 쿠폰은 환원이 되지 않습니다. 이에 유의해주시기 바랍니다.
                                                        <br /><br />

                                                        단, 당사의 사유로 부득이하게 발생된 도서품절, 재고오류, 파본 등으로 발생된 사유는 해당도서
                                                        주문시 사용된 무료배송 쿠폰이 자동환원됩니다.
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <button @click="btnToggle4" class="con-open_close">
                                        <img :src="conOpenBotton" alt="con-open" />
                                        <img class="con-close" :src="conCloseBotton" alt="con-close" />
                                    </button>
                                </div>
                                <!-- 여기까지 -->
                                <!-- 여기부터555555555555555 -->
                                <div class="qna-list">
                                    <div class="qna-box">
                                        <!-- 대기중 아이콘 -->
                                        <div>
                                            <img class="done-icon" src="../assets/img/b5.png" alt="done-img" />
                                        </div>
                                        <div class="qna_title_rep">
                                            <div class="qna-title">검색이 되지 않거나 품절/절판인 도서는 구매할 수 없나요?</div>
                                            <div class="younju" v-if="showFaqContent5">
                                                <div class="qna-reply">
                                                    <div class="qna_reply_text">
                                                        검색이 되지 않는 도서는 품절/절판일 경우가 대부분입니다.<br /><br />

                                                        품절이나 절판으로 표시된 도서는 출판사에서도 더 이상 발간하지 않고 재고가 없는 경우가
                                                        대부분이라 구하기 어려운 도서이기 때문에 이 경우 회원님께서 요청하셔도 구입시기를 확실하게
                                                        약속드릴 수 없습니다. <br /><br />

                                                        이러한 도서의 구입가능 여부를 확인하시려면 고객센터의 1:1 문의 접수 이용하여 주시면, 최대한
                                                        확인하여 답변드리겠습니다.
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <button @click="btnToggle5" class="con-open_close">
                                        <img :src="conOpenBotton" alt="con-open" />
                                        <img class="con-close" :src="conCloseBotton" alt="con-close" />
                                    </button>
                                </div>
                                <!-- 여기까지 -->
                            </div>
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
import myPageSide from "@/components/myPageSide.vue";
import myPage_top from "@/components/myPage_top.vue";
import Floating from "../components/floating.vue";
import ScrollTop from "@/components/scrollTop.vue";

export default {
    components: {
        GnbBar,
        TabsWrapper,
        TabItem,
        myPage_top,
        myPageSide,
        Floating,
        ScrollTop,
    },
    data() {
        return {
            conOpenBotton: require("../assets/img/qna-open.png"),
            conCloseBotton: require("../assets/img/qna-close.png"),
            showFaqContent1: false,
            showFaqContent2: false,
            showFaqContent3: false,
            showFaqContent4: false,
            showFaqContent5: false,
        };
    },
    methods: {
        btnToggle1() {
            this.showFaqContent1 = !this.showFaqContent1;
        },
        btnToggle2() {
            this.showFaqContent2 = !this.showFaqContent2;
        },
        btnToggle3() {
            this.showFaqContent3 = !this.showFaqContent3;
        },
        btnToggle4() {
            this.showFaqContent4 = !this.showFaqContent4;
        },
        btnToggle5() {
            this.showFaqContent5 = !this.showFaqContent5;
        },
    },
};
</script>
