<template>
    <div>전체 주문 내역</div>
    <div>
        <div class="filter-container">
            <div class="date-filter">
                <label>날짜 선택:</label>
                <input type="date" v-model="startDate" />
                ~
                <input type="date" v-model="endDate" />
                <button class="orderinfo_btn" @click="fetchFilteredOrders">검색</button>
            </div>

            <div class="order-search">
                <label>주문 검색:</label>
                <input type="text" v-model="orderKeyword" />
                <button class="orderinfo_btn" @click="searchOrders">검색</button>
            </div>

            <div class="status-filter">
                <label>배송 상태별 조회:</label>
                <select v-model="selectedStatus">
                    <option value="ready">배송준비</option>
                    <option value="delivering">배송중</option>
                    <option value="delivered">배송완료</option>
                    <option value="cancelled">주문취소</option>
                </select>
                <button class="orderinfo_btn" @click="fetchOrdersByStatus">조회</button>
            </div>
        </div>
    </div>

    <div class="order-list">
        <div v-for="order in orderList" :key="order.ORDER_ID" class="order-box">
            <!-- 왼쪽에 배송 상태 및 결제일 -->
            <div class="order-status">
                <p>배송상태: {{ order.ORDER_STATE }}</p>
                <p>결제일: {{ order.ORDER_DATE }}</p>
            </div>

            <!-- 왼쪽에 책 표지 -->
            <div class="book-cover">
                <img :src="order.BOOK_COVER" alt="Book Cover" />
            </div>

            <!-- 오른쪽에 책 정보 -->
            <div class="book-info">
                <p>책 제목: {{ order.BOOK_TITLE }}</p>
                <p>가격: {{ order.BOOK_PRICE }}</p>
                <p>주문 상세: {{ order.ORDER_DETAIL }}</p>
            </div>

            <!-- 구매확정 버튼 -->
            <button v-if="order.ORDER_STATE === '배송완료'" class="confirm-button">구매확정</button>
        </div>
    </div>
</template>
<script>
import axios from "axios";

export default {
    data() {
        return {
            startDate: null,
            endDate: null,
            orderKeyword: "",
            selectedStatus: "ready", // 기본값은 '배송준비'
            orderList: [], // 주문 목록을 저장할 배열
        };
    },
    methods: {
        fetchFilteredOrders() {
            // 선택한 날짜 범위로 주문 목록을 가져오는 로직
            // axios.get(...)를 사용하여 API 호출
        },
        searchOrders() {
            // 주문 검색 로직
            // axios.get(...)를 사용하여 API 호출
        },
        fetchOrdersByStatus() {
            // 선택한 배송 상태별로 주문 목록을 가져오는 로직
            // axios.get(...)를 사용하여 API 호출
        },
        // 주문 목록을 표시하는 코드 및 API 호출 메서드 추가
    },
};
</script>
<style></style>
