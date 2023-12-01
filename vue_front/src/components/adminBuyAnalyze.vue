<style scoped src="bootstrap/dist/css/bootstrap.css"></style>
<style scoped src="../assets/css/sbadmin.css"></style>

<template>
    <body id="page-top">
        <!-- Page Wrapper -->
        <div id="wrapper">
            <!-- Sidebar -->
            <SideBar />
            <!-- End of Sidebar -->
            <!-- Content Wrapper -->
            <div id="content-wrapper" class="d-flex flex-column">
                <!-- Main Content -->
                <div id="content">
                    <!-- Topbar -->
                    <nav
                        class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow d-flex align-items-center justify-content-center"
                    >
                        <h1><a class="text-gray-800" href="/" style="text-decoration: none">MOONMOON</a></h1>
                    </nav>
                    <!-- End of Topbar -->

                    <!-- Begin Page Content -->
                    <div class="container-fluid">
                        <!-- Page Heading -->
                        <div class="d-sm-flex align-items-center justify-content-between mb-4">
                            <h1 class="h3 mb-0 text-gray-800">구매 분석</h1>
                        </div>

                        <!-- Content Row -->
                        <div class="row d-flex align-items-center justify-content-center">
                            <div v-for="(item, i) in bestCategorys" :key="i" class="mb-4" style="width: 12%">
                                <div
                                    class="card shadow h-100 mb-5"
                                    :class="{
                                        'cardshadow h-100 mb-5 border-bottom-danger': i + 1 === 1,
                                        'cardshadow h-100 mb-5 border-bottom-secondary': i + 1 === 2,
                                        'cardshadow h-100 mb-5 border-bottom-warning': i + 1 === 3,
                                        'cardshadow h-100 mb-5 border-bottom-primary': i + 1 === 4,
                                        'cardshadow h-100 mb-5 border-bottom-info': i + 1 === 5,
                                    }"
                                >
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div
                                                    class="text-xs font-weight-bold text-secondary text-uppercase mb-1"
                                                    :class="{
                                                        'text-danger': i + 1 === 1,
                                                        'text-secondary': i + 1 === 2,
                                                        'text-warning': i + 1 === 3,
                                                        'text-primary': i + 1 === 4,
                                                        'text-info': i + 1 === 5,
                                                    }"
                                                >
                                                    {{ item.category }}
                                                </div>
                                                <div class="row no-gutters align-items-center">
                                                    <div class="col-auto">
                                                        <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">{{ item.sales }}개</div>
                                                    </div>
                                                    <div class="col">
                                                        <div class="progress progress-sm mr-2">
                                                            <div
                                                                class="progress-bar bg-warning"
                                                                role="progressbar"
                                                                style="width: 50%"
                                                                aria-valuenow="50"
                                                                aria-valuemin="0"
                                                                aria-valuemax="100"
                                                            ></div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fa-solid fa-book fa-xl text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row d-flex align-items-center justify-content-center">
                            <!-- Area Chart -->
                            <div class="col-xl-8 col-lg-7">
                                <div class="card shadow mb-4">
                                    <!-- Card Header - Dropdown -->
                                    <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                        <h6 class="m-0 font-weight-bold text-primary">많이 구매한 책 장르</h6>
                                    </div>
                                    <!-- Card Body -->
                                    <div class="card-body">
                                        <div class="chart-area d-flex align-items-center justify-content-center">
                                            <canvas ref="bestCategoryChart" width="1000" height="300"></canvas>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.container-fluid -->
                </div>
                <!-- End of Main Content -->

                <!-- Footer -->
                <footer class="sticky-footer bg-white">
                    <div class="container my-auto">
                        <div class="copyright text-center my-auto">
                            <span>Copyright &copy; Your Website 2023</span>
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
    </body>
</template>

<script>
import { ref, onMounted, reactive } from "vue";
import Chart from "chart.js/auto";
import "bootstrap"; // Import Bootstrap JavaScript
import "chartjs-adapter-luxon";
import SideBar from "../components/adminSidebar.vue";
import axios from "axios";

export default {
    components: { SideBar },
    data() {
        return {
            //책 장르 데이터
            bestCategorys: [
                {
                    category: "장르",
                    sales: 0,
                },
                {
                    category: "장르",
                    sales: 0,
                },
                {
                    category: "장르",
                    sales: 0,
                },
                {
                    category: "장르",
                    sales: 0,
                },
                {
                    category: "장르",
                    sales: 0,
                },
            ],
        };
    },
    setup() {
        const bestCategoryChart = ref(null);

        //방문자 수 데이터
        var bestCategorys = reactive([
            {
                category: "장르",
                sales: 0,
            },
            {
                category: "장르",
                sales: 0,
            },
            {
                category: "장르",
                sales: 0,
            },
            {
                category: "장르",
                sales: 0,
            },
            {
                category: "장르",
                sales: 0,
            },
        ]);

        //----------------------------------여기부터 서버에서 데이터 불러오는 함수-------------------------------------

        // 주간 방문자 수 데이터를 업데이트하는 함수 정의
        const updateBestCategory = async () => {
            try {
                const response = await axios.post("http://localhost:3000/admin/bestCategory", {});
                const data = response.data;

                // 데이터를 주간 방문자 수 배열에 할당
                bestCategorys = data.map((item) => {
                    return {
                        category: item.BOOK_CATEGORYNAME,
                        sales: item.TOTAL_SALES,
                    };
                });
                // 대분류만 가져오기위해 문자열 잘라내기
                const extractedCategories = bestCategorys.map((item) => {
                    const match = item.category.match(/국내도서>(.*?)>/); // 정규식을 사용해 "국내도서>" 다음에 오는 문자열을 추출
                    const newCategory = match ? match[1].trim() : ""; // 공백을 제거한 후 새로운 카테고리 값으로 설정
                    return {
                        ...item, // 원래 항목의 속성들을 그대로 가져오고
                        category: newCategory, // category 속성을 새로운 값으로 업데이트
                    };
                });

                // extractedCategories로 bestCategorys 배열을 업데이트
                bestCategorys = extractedCategories;

                // 차트를 다시 그리는 함수 호출
                drawBestCategoryChart();
            } catch (error) {
                alert("?");
                console.error(error);
            }
        };

        //----------------------------------여기부터 차트 함수-------------------------------------

        const drawBestCategoryChart = () => {
            // 이전 차트 인스턴스 제거
            if (bestCategoryChart.value && bestCategoryChart.value.chart) {
                bestCategoryChart.value.chart.destroy();
            }

            if (bestCategoryChart) {
                const ctx = bestCategoryChart.value.getContext("2d");
                bestCategoryChart.value.chart = new Chart(ctx, {
                    type: "bar",
                    data: {
                        labels: [
                            bestCategorys[0].category,
                            bestCategorys[1].category,
                            bestCategorys[2].category,
                            bestCategorys[3].category,
                            bestCategorys[4].category,
                        ],
                        datasets: [
                            {
                                label: "구매 수", // Add dataset label
                                data: [
                                    bestCategorys[0].sales,
                                    bestCategorys[1].sales,
                                    bestCategorys[2].sales,
                                    bestCategorys[3].sales,
                                    bestCategorys[4].sales,
                                ],
                                backgroundColor: [
                                    "#E74A3B", // 첫 번째 막대의 배경색
                                    "#858796", // 두 번째 막대의 배경색
                                    "#F6C23E", // 세 번째 막대의 배경색
                                    "#4E73DF", // 네 번째 막대의 배경색
                                    "#36B9CC", // 다섯 번째 막대의 배경색
                                ],
                                borderColor: [
                                    "#E74A3B", // 막대 테두리 색상
                                    "#858796",
                                    "#F6C23E",
                                    "#4E73DF",
                                    "#36B9CC",
                                ],
                                hoverOffset: 1,
                                fill: true,
                            },
                        ],
                    },
                    options: {
                        responsive: false,
                        scales: {
                            x: {
                                grid: {
                                    display: false, // 배경 세로 선 숨기기
                                },
                            },
                            y: {
                                beginAtZero: true, // y 축의 값이 0부터 시작하도록 설정
                            },
                        },
                        plugins: {
                            tooltip: {
                                padding: 10,
                            },
                        },
                    },
                });
            }
        };

        onMounted(() => {
            updateBestCategory();
        });

        return {
            bestCategoryChart,
        };
    },
    mounted() {
        this.updateBestCategory();
    },
    methods: {
        // 주간 방문자수 데이터 받기
        async updateBestCategory() {
            await axios({
                url: "http://localhost:3000/admin/bestCategory",
                method: "POST",
            })
                .then((res) => {
                    const data = res.data;

                    // 데이터를 주간 방문자 수 배열에 할당
                    this.bestCategorys = data.map((item) => {
                        return {
                            category: item.BOOK_CATEGORYNAME,
                            sales: item.TOTAL_SALES,
                        };
                    });
                    // 대분류만 가져오기위해 문자열 잘라내기
                    const extractedCategories = this.bestCategorys.map((item) => {
                        const match = item.category.match(/국내도서>(.*?)>/); // 정규식을 사용해 "국내도서>" 다음에 오는 문자열을 추출
                        const newCategory = match ? match[1].trim() : ""; // 공백을 제거한 후 새로운 카테고리 값으로 설정
                        return {
                            ...item, // 원래 항목의 속성들을 그대로 가져오고
                            category: newCategory, // category 속성을 새로운 값으로 업데이트
                        };
                    });

                    // extractedCategories로 bestCategorys 배열을 업데이트
                    this.bestCategorys = extractedCategories;
                    console.log(this.bestCategorys, "베스틉카테코리쓰");
                })
                .catch((err) => {
                    alert(err);
                });
        },
        formatNumber(number) {
            // 숫자를 천 단위마다 쉼표가 있는 형식으로 변환
            return new Intl.NumberFormat().format(number);
        },
    },
};
</script>
