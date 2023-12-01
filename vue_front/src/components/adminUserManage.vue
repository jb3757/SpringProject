<style scoped src="../../sb-admin-2/vendor/datatables/dataTables.bootstrap4.min.css"></style>
<style scoped src="../assets/css/sbadmin.css"></style>


<template>
    <body id="page-top">

        <!-- Page Wrapper -->
        <div id="wrapper">

            <!-- Sidebar -->
            <SideBar />
            <!-- End Sidebar -->

            <!-- Content Wrapper -->
            <div id="content-wrapper" class="d-flex flex-column">

                <!-- Main Content -->
                <div id="content">

                    <!-- Topbar -->
                    <nav
                        class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow d-flex align-items-center justify-content-center">
                        <h1><a class="text-gray-800" href="/" style="text-decoration: none;">MOONMOON</a></h1>
                    </nav>
                    <!-- End of Topbar -->

                    <!-- Begin Page Content -->
                    <div class="container-fluid">

                        <!-- Page Heading -->
                        <div class="d-flex align-items-center justify-content-between mb-3">
                            <h1 class="h3 mb-2 text-gray-800 col-4">회원 관리</h1>

                        </div>

                        <!-- DataTales Example -->
                        <div class="card shadow mb-4">
                            <div class="card-header py-3 d-flex align-items-center justify-content-between">
                                <h6 class="m-0 font-weight-bold text-primary">회원 목록</h6>

                                <div class="btn-group">
                                    <select class="btn btn-secondary dropdown-toggle" @change="getUserData"
                                        v-model="filterValue">
                                        <option class="dropdown-item btn cursor-pointer" value="등급">등급</option>
                                        <option class="dropdown-item btn cursor-pointer" value="프렌즈">프렌즈</option>
                                        <option class="dropdown-item btn cursor-pointer" value="실버">실버</option>
                                        <option class="dropdown-item btn cursor-pointer" value="골드">골드</option>
                                        <option class="dropdown-item btn cursor-pointer" value="플래티넘">플래티넘</option>
                                    </select>
                                </div>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                        <thead>
                                            <tr style="font-weight: bold;">
                                                <th>이름</th>
                                                <th>성별</th>
                                                <th>나이대</th>
                                                <th>이메일</th>
                                                <th>회원 등급</th>
                                                <th>핸드폰 번호</th>
                                                <th>가입날짜</th>
                                                <th>적립금</th>
                                                <th>주소</th>
                                                <th>우편번호</th>
                                            </tr>
                                        </thead>
                                        <tfoot>
                                            <tr style="font-weight: bold;">
                                                <th>이름</th>
                                                <th>성별</th>
                                                <th>나이대</th>
                                                <th>이메일</th>
                                                <th>회원 등급</th>
                                                <th>핸드폰 번호</th>
                                                <th>가입날짜</th>
                                                <th>적립금</th>
                                                <th>주소</th>
                                                <th>우편번호</th>
                                            </tr>
                                        </tfoot>
                                        <tbody>
                                            <tr ref="userData" v-for="(item, i) in displayedPosts" :key="i">
                                                <td class="col-1 align-middle" style="font-size: small;">{{
                                                    item.USER_NAME }}</td>
                                                <td v-if="item.USER_SEX == 'm'" class="col-1 align-middle"
                                                    style="font-size: small;">여성</td>
                                                <td v-else-if="item.USER_SEX == 'f'" class="col-1 align-middle"
                                                    style="font-size: small;">남성</td>
                                                <td class="col-1 align-middle" style="font-size: small;">
                                                    {{ item.USER_AGEGROUP }}</td>
                                                <td class="col-2 align-middle" style="font-size: small;">
                                                    {{ item.USER_EMAIL }}</td>
                                                <td class="col-1 align-middle" :style="{
                                                    color: item.USER_TOTAL_PAY === '프렌즈' ? '#47C867' :
                                                        item.USER_TOTAL_PAY === '실버' ? '#8F99BF' :
                                                            item.USER_TOTAL_PAY === '골드' ? '#F6A621' :
                                                                item.USER_TOTAL_PAY === '플래티넘' ? '#5D59E1' : 'black'
                                                }">{{ item.USER_TOTAL_PAY }}</td>
                                                <td class="col-1 align-middle" style="font-size: small;">{{
                                                    item.USER_PHONE }}</td>
                                                <td class="col-1 align-middle" style="font-size: small;">{{
                                                    item.USER_REGDATE }}</td>
                                                <td class="col-1 align-middle" style="font-size: small;">
                                                    {{ formatNumber(item.USER_POINT) }}</td>
                                                <td class="col-2 align-middle" style="font-size: small;">
                                                    {{ item.USER_ADD1 }} {{ item.USER_ADD2 }}</td>
                                                <td class="col-1 align-middle" style="font-size: small;">{{
                                                    item.USER_ZIPCODE }}
                                                </td>
                                                <td class="d-flex align-items-center border-0 col-1"><button
                                                        class="btn btn-danger btn-circle btn-sm  p-3"
                                                        @click="deleteUserData(item.USER_EMAIL)"><i
                                                            class="fa-solid fa-user-minus"></i></button></td>
                                                <!-- <td class="d-flex align-items-center border-0 col-1"><button
                                                        class="btn btn-danger btn-circle btn-sm  p-3"
                                                        @click="deleteBookData(item.BOOK_ID)"><i
                                                            class="fas fa-trash"></i></button></td> -->
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                    </div>
                    <!-- /.container-fluid -->
                    <nav class="d-flex align-items-center justify-content-center" aria-label="Page navigation example">
                        <ul class="pagination">
                            <li class="page-item">
                                <button class="page-link" @click="changePage('first')"
                                    :disabled="currentPage === 1">맨앞</button>
                            </li>
                            <li class="page-item">
                                <button class="page-link" @click="changePage(currentPage - 1)"
                                    :disabled="currentPage === 1">이전</button>
                            </li>
                            <li class="page-item d-flex">
                                <button class="page-link" v-for="pageNumber in pageNumbers" :key="pageNumber"
                                    @click="changePage(pageNumber)"
                                    :class="{ 'active': pageNumber === currentPage, 'disabled': pageNumber === currentPage || currentPage === 1 || currentPage === totalPages }">
                                    {{ pageNumber }}
                                </button>
                            </li>
                            <li class="page-item">
                                <button class="page-link" @click="changePage(currentPage + 1)"
                                    :disabled="currentPage === totalPages">다음</button>
                            </li>
                            <li class="page-item">
                                <button class="page-link" @click="changePage('last')"
                                    :disabled="currentPage === totalPages">맨뒤</button>
                            </li>
                        </ul>

                    </nav>
                </div>
                <!-- End of Main Content -->

                <!-- Footer -->
                <footer class="sticky-footer bg-white">
                    <div class="container my-auto">
                        <div class="copyright text-center my-auto">
                            <span>Copyright &copy; Your Website 2020</span>
                        </div>
                    </div>
                </footer>
                <!-- End of Footer -->

            </div>
            <!-- End of Content Wrapper -->

        </div>
        <!-- End of Page Wrapper -->

        <!-- Scroll to Top Button-->
        <a class="scroll-to-top rounded" href="#page-top">
            <i class="fas fa-angle-up"></i>
        </a>

        <!-- Logout Modal-->
        <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
            aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                        <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
                    <div class="modal-footer">
                        <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                        <a class="btn btn-primary" href="login.html">Logout</a>
                    </div>
                </div>
            </div>
        </div>

    </body>
</template>

<script>
import axios from "axios";
axios.defaults.baseURL = "http://localhost:3000";
axios.defaults.headers.post["Content-Type"] = "application/json;charset=utf-8";
axios.defaults.headers.post["Access-Control-Allow-Origin"] = "*";

import SideBar from '../components/adminSidebar.vue';

export default {
    name: '',
    components: { SideBar },
    data() {
        return {
            userData: [],

            //정렬
            filterValue: "등급",

            // 페이징
            perPage: 10, // 한 페이지에 보여줄 게시물 수
            currentPage: 1, // 현재 페이지 번호
            maxDisplayedPages: 9, // 표시할 최대 페이지 수 (현재 페이지를 중심으로 좌우로 표시)
        };
    },

    setup() { },
    created() { },
    mounted() {
        this.getUserData();
    },
    unmounted() { },
    computed: {
        totalPages() {
            return Math.ceil(this.userData.length / this.perPage);
        },
        displayedPosts() {
            const start = Math.max(0, (this.currentPage - 1) * this.perPage);
            const end = Math.min(this.userData.length, this.currentPage * this.perPage);
            return this.userData.slice(start, end);
        },
        pageNumbers() {
            const start = Math.max(1, this.currentPage - Math.floor(this.maxDisplayedPages / 2));
            const end = Math.min(this.totalPages, start + this.maxDisplayedPages - 1);
            return Array.from({ length: end - start + 1 }, (_, i) => start + i);
        },
    },
    methods: {
        async getUserData() {
            await axios({
                url: "http://localhost:3000/admin/adminUserLatest",
                method: "POST",
                data: {
                },
            })
                .then((res) => {
                    console.log(res.data)

                    this.userData = res.data.map(user => {
                        if (user.USER_TOTAL_PAY <= 100000) {
                            user.USER_TOTAL_PAY = "프렌즈";
                        } else if (user.USER_TOTAL_PAY >= 100000 && user.USER_TOTAL_PAY < 200000) {
                            user.USER_TOTAL_PAY = "실버";
                        } else if (user.USER_TOTAL_PAY >= 200000 && user.USER_TOTAL_PAY < 300000) {
                            user.USER_TOTAL_PAY = "골드";
                        } else {
                            user.USER_TOTAL_PAY = "플래티넘";
                        }
                        return user;
                    });

                    console.log("이전 userData:", this.userData); // 필터링 전 데이터
                    if (this.filterValue === "프렌즈") {
                        this.userData = this.userData.filter(user => user.USER_TOTAL_PAY === "프렌즈");
                    } else if (this.filterValue === "실버") {
                        this.userData = this.userData.filter(user => user.USER_TOTAL_PAY === "실버");
                    } else if (this.filterValue === "골드") {
                        this.userData = this.userData.filter(user => user.USER_TOTAL_PAY === "골드");
                    } else if (this.filterValue === "플래티넘") {
                        this.userData = this.userData.filter(user => user.USER_TOTAL_PAY === "플래티넘");
                    }
                    console.log("변경된 userData:", this.userData); // 필터링 후 데이터
                })
                .catch((err) => {
                    alert(err);
                });
        },
        changeUserData() {

            console.log(this.filterValue, "필터밸류");
        },
        async deleteUserData(userEmail) { //회원 탈퇴
            await axios({
                url: "http://localhost:3000/admin/adminUserDel",
                method: "POST",
                data: {
                    userEmail: userEmail,
                },
            })
                .then((res) => {
                    alert(userEmail + " 회원을 탈퇴시켰습니다.");
                    this.getUserData();
                })
                .catch((err) => {
                    alert(err);
                });
        },
        formatNumber(number) {
            // 숫자를 천 단위마다 쉼표가 있는 형식으로 변환
            return new Intl.NumberFormat().format(number);
        },
        changePage(pageNumber) {
            if (pageNumber === 'first') {
                pageNumber = 1;
            } else if (pageNumber === 'last') {
                pageNumber = this.totalPages;
            } else {
                pageNumber = parseInt(pageNumber);
            }

            window.scrollTo({ top: 0, behavior: 'auto' });
            if (pageNumber >= 1 && pageNumber <= this.totalPages) {
                this.currentPage = pageNumber;
            }
        },
    }
}
</script>