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
                            <h1 class="h3 mb-2 text-gray-800 col-4">문의 관리</h1>

                        </div>

                        <!-- DataTales Example -->
                        <div class="card shadow mb-4">
                            <div class="card-header py-3 d-flex align-items-center justify-content-between">
                                <h6 class="m-0 font-weight-bold text-primary">문의 목록</h6>

                                <div class="btn-group">
                                    <select class="btn btn-secondary dropdown-toggle" @change="getQnaData"
                                        v-model="filterValue">
                                        <option class="dropdown-item btn cursor-pointer" value="답변 대기">답변 대기</option>
                                        <option class="dropdown-item btn cursor-pointer" value="답변 완료">답변 완료</option>
                                    </select>
                                </div>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                        <thead>
                                            <tr style="font-weight: bold;">
                                                <th>문의 번호</th>
                                                <th>문의 제목</th>
                                                <th>문의 내용</th>
                                                <th>문의 날짜</th>
                                                <th>고객 이메일</th>
                                                <th>답변 여부</th>
                                            </tr>
                                        </thead>
                                        <tfoot>
                                            <tr style="font-weight: bold;">
                                                <th>문의 번호</th>
                                                <th>문의 제목</th>
                                                <th>문의 내용</th>
                                                <th>문의 날짜</th>
                                                <th>고객 이메일</th>
                                                <th>답변 여부</th>

                                            </tr>
                                        </tfoot>
                                        <tbody>
                                            <tr ref="userData" v-for="(item, i) in displayedPosts" :key="i">
                                                <td class="col-1 align-middle" style="font-size: small;">{{
                                                    item.QNA_ID }}</td>
                                                <td class="col-2 align-middle" style="font-size: small;">
                                                    {{ item.QNA_TITLE }}</td>
                                                <td class="col-5 align-middle" style="font-size: small;">
                                                    {{ item.QNA_CON }}</td>
                                                <td class="col-1 align-middle" style="font-size: small;">
                                                    {{ item.QNA_DATE }}</td>
                                                <td class="col-1 align-middle" style="font-size: small;">
                                                    {{ item.QNA_USER_EMAIL }}</td>
                                                <td v-if="item.QNA_REP === 0" class="col-1 align-middle"
                                                    style="font-size: small;">
                                                    대기중</td>
                                                <td v-else class="col-1 align-middle" style="font-size: small;">
                                                    완료</td>
                                                <button v-if="item.QNA_REP === 0" type="button" class="btn btn-primary align-middle ml-2"
                                                    data-bs-toggle="modal" data-bs-target="#exampleModal">
                                                    답변하기
                                                </button>
                                                <!-- 모달 -->
                                                <div class="modal fade position-fixed" id="exampleModal" tabindex="-1"
                                                    aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                    <div class="modal-dialog col-8" style="top: 15%; left: 0">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h1 class="modal-title fs-5" id="exampleModalLabel">문의
                                                                    답변하기</h1>
                                                            </div>
                                                            <div class="modal-body">
                                                                <form>
                                                                    <div class="mb-3">
                                                                        <label for="message-text"
                                                                            class="col-form-label">답변내용 : </label>
                                                                        <textarea v-model="answerDetail" class="form-control"
                                                                            id="message-text" rows="8"></textarea>
                                                                    </div>
                                                                </form>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary"
                                                                    data-bs-dismiss="modal">닫기</button>
                                                                <button @click="sendQnaAnswer(item.QNA_ID)" type="button" class="btn btn-primary" data-bs-dismiss="modal">완료</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>


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
            qnaData: [],

            //답변내용
            answerDetail: "",

            //정렬
            filterValue: "답변 대기",

            // 페이징
            perPage: 10, // 한 페이지에 보여줄 게시물 수
            currentPage: 1, // 현재 페이지 번호
            maxDisplayedPages: 9, // 표시할 최대 페이지 수 (현재 페이지를 중심으로 좌우로 표시)
        };
    },

    setup() { },
    created() { },
    mounted() {
        this.getQnaData();
    },
    unmounted() { },
    computed: {
        totalPages() {
            return Math.ceil(this.qnaData.length / this.perPage);
        },
        displayedPosts() {
            const start = Math.max(0, (this.currentPage - 1) * this.perPage);
            const end = Math.min(this.qnaData.length, this.currentPage * this.perPage);
            return this.qnaData.slice(start, end);
        },
        pageNumbers() {
            const start = Math.max(1, this.currentPage - Math.floor(this.maxDisplayedPages / 2));
            const end = Math.min(this.totalPages, start + this.maxDisplayedPages - 1);
            return Array.from({ length: end - start + 1 }, (_, i) => start + i);
        },
    },
    methods: {
        async getQnaData() {
            if (this.filterValue === "답변 대기") {
                await axios({
                    url: "http://localhost:3000/admin/adminQnaWait",
                    method: "POST",
                    data: {
                    },
                })
                    .then((res) => {
                        this.qnaData = res.data;
                        console.log(res.data);
                    })
                    .catch((err) => {
                        alert(err);
                    });
            } else if (this.filterValue === "답변 완료") {
                await axios({
                    url: "http://localhost:3000/admin/adminQnaDone",
                    method: "POST",
                    data: {
                    },
                })
                    .then((res) => {
                        this.qnaData = res.data;
                        console.log(res.data);
                    })
                    .catch((err) => {
                        alert(err);
                    });
            }

        },
        async sendQnaAnswer(qnaId) {
            await axios({
                    url: "http://localhost:3000/admin/adminQnaReply",
                    method: "POST",
                    data: {
                        qna_id: qnaId,
                        qna_reply: this.answerDetail
                    },
                })
                    .then((res) => {
                        alert("답변 완료했습니다.");
                        this.getQnaData();
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