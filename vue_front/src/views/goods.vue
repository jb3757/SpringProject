<style src="../assets/css/goods.css"></style>

<template>
    <GnbBar />
    <div class="goods">
        <div class="goods_header">
            <h2>굿즈</h2>
        </div>
        <div class="goods_items">
            <div v-for="goods_inside in goods" class="goods_item">
                <div class="goods_item_img">
                    <a href=""><img :src="require(`../assets/img/${goods_inside.BOOK_COVER}`)" alt="" /></a>
                </div>
                <div class="goods_item_info">
                    <h2 class="best_item_info_title">
                        <a href="">{{ goods_inside.BOOK_TITLE }}</a>
                    </h2>
                    <div class="goods_item_info_price">
                        <span>{{ comma(goods_inside.BOOK_PRICE) }}</span>
                        <span>원</span>
                    </div>
                    <p class="goods_item_info_summary">
                        <!-- {{ goods_inside.BOOK_DESCRIPTION }} -->
                    </p>
                </div>
                <div class="goods_item_order">
                    <a href="">결제하러 가기</a>
                    <p>
                        <span>내일({{ month }}/{{ nextDay }})</span> 도착예정
                    </p>
                </div>
                <div class="goods_item_btn">
                    <a href=""><img src="../assets/img/heart.png" alt="" /></a>
                    <a href=""><img src="../assets/img/cart2.png" alt="" /></a>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import GnbBar from "../components/gnbBar.vue";

export default {
    name: "",
    components: { GnbBar },
    data() {
        return {
            goods: [
                { BOOK_TITLE: "텀블러", BOOK_PRICE: 16500, BOOK_COVER: "mockup_tum.png" },
                { BOOK_TITLE: "노트", BOOK_PRICE: 12000, BOOK_COVER: "mockup_book.png" },
                { BOOK_TITLE: "에코백", BOOK_PRICE: 11500, BOOK_COVER: "mockup_eco.png" },
                { BOOK_TITLE: "책갈피", BOOK_PRICE: 2000, BOOK_COVER: "mockup_bookmark.png" },
            ],
            nextDay: "",
            month: "",
        };
    },

    setup() {},
    created() {},
    mounted() {
        // this.getGoods();
        this.getNextDate();
    },
    unmounted() {},
    methods: {
        comma(num) {
            return String(num).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        },
        // getGoods() {
        //     axios({
        //         url: "/goods",
        //         method: "POST",
        //         data: {},
        //     }).then((res) => {
        //         this.goods = res.data;
        //     });
        // },
        getNextDate() {
            //내일 배송일로 나오게 하는 함수
            const today = new Date();
            this.nextDay = today.getDate() + 1;
            this.month = today.getMonth() + 1;
        },
    },
};
</script>
