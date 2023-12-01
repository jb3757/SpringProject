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
            class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow d-flex align-items-center justify-content-center">
            <h1 class="">MOONMOON</h1>
          </nav>
          <!-- End of Topbar -->

          <!-- Begin Page Content -->
          <div class="container-fluid d-flex flex-column align-items-center justify-content-center">

            <!-- Page Heading -->
            <div class="d-sm-flex align-items-center justify-content-between mb-4">
              <h1 class="h3 mb-0 text-gray-800">책 추가</h1>
            </div>

            <div class="input_book_title">
              <form class="book_title_form d-flex align-items-center" @submit.prevent="book_research">
                <input type="text" class="book_title" v-model="bookname" placeholder="책이름을 입력하세요" />
                <button class="bi_button ml-2 btn btn-primary" type="submit">검색</button>
              </form>
            </div>
            <div v-if="bookResults.length > 0" class="book_selection">
              <label class="bi_label" for="selectedBook">Select a book:</label>
              <select class="bi_select" v-model="selectedBook" @change="onBookSelectionChange">
                <option value="default">-----------------------</option>
                <option v-for="(book, index) in bookResults" :key="index" :value="index">
                  {{ book.title }}
                </option>
              </select>
            </div>
            <div class="info_all 1">
              <div class="book_info_image">
                <img v-if="book_image" :src="book_image" class="book_image" />
                <p v-else class="book_non_image">이미지없음</p>
                <p>이미지 제공 api: {{ book_provider }}</p>
              </div>
              <div class="info_text">
                <div class="con_line d-flex align-items-baseline">
                  <h6 class="bi_h6">책 이름:</h6>
                  <div class="contents">{{ book_title }}</div>
                </div>
                <div class="con_line d-flex align-items-baseline">
                  <h6 class="bi_h6">작가:</h6>
                  <div class="contents">{{ book_author }}</div>
                </div>
                <div class="con_line d-flex align-items-baseline">
                  <h6 class="bi_h6">정가:</h6>
                  <div class="contents">{{ book_pricestandard }}</div>
                </div>
                <div class="con_line d-flex align-items-baseline">
                  <h6 class="bi_h6">카테고리:</h6>
                  <div class="contents">{{ book_categoryname }}</div>
                </div>
                <div class="con_line d-flex align-items-baseline">
                  <h6 class="bi_h6">포인트:</h6>
                  <div class="contents">{{ book_point }}</div>
                </div>
                <div class="con_line d-flex align-items-baseline">
                  <h6 class="bi_h6">출판사:</h6>
                  <div class="contents">{{ book_publisher }}</div>
                </div>
                <div class="con_line d-flex align-items-baseline">
                  <h6 class="bi_h6">출판일:</h6>
                  <div class="contents">{{ book_pubdate }}</div>
                </div>
                <div class="con_line d-flex align-items-baseline">
                  <h6 class="line bi_h6">알라딘<br />판매지수:</h6>
                  <div class="contents">{{ book_salesPoint }}</div>
                </div>
                <div class="con_line d-flex align-items-baseline">
                  <h6 class="bi_h6">책 한줄 설명:</h6>
                  <div class="contents">{{ book_description }}</div>
                </div>
              </div>
            </div>
            <button type="button" @click="insertBookInfo" class="insertBookInfo bi_button mt-5 btn btn-primary">
              등록하기
            </button>
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
import SideBar from '../components/adminSidebar.vue';

import "@/assets/css/bookInsert.css";
import axios from "axios";
import 'bootstrap'; // Import Bootstrap JavaScript

axios.defaults.baseURL = "http://localhost:3000";
axios.defaults.headers.post["Content-Type"] = "application/json;charset=utf-8";
axios.defaults.headers.post["Access-Control-Allow-Origin"] = "*";

export default {
  components: { SideBar },
  data() {
    return {
      bookname: "",
      email: "",
      password: "",
      provider: "",
      book_title: "",
      book_cover: "",
      book_author: "",
      book_pubdate: "",
      book_pricesales: "",
      book_pricestandard: "",
      book_categoryname: "",
      book_point: "",
      book_publisher: "",
      bookResults: [],
      naverResults: [],
      selectedBook: null,
      book_provider: "",
      book_image: "",
      book_salesPoint: "",
      book_description: "",
    };
  },
  methods: {
    book_research() {
      //검색 시 입력창에 있는 걸로 알라딘 api 검색 실시합니다.
      axios({
        url: "/aladin/getInfo",
        method: "POST",
        data: {
          bookname: this.bookname, //서버으로 넘깁니다.
        },
      })
        .then((res) => {
          let aladinXml = [];
          aladinXml = res.data; //서버에서 json으로 검색 결과를 넘긴걸 변수에 넣습니다.
          //   console.log(aladinXml.object.totalResults._text);
          //   console.log(aladinXml.object.item.title._text);
          if (aladinXml.object.totalResults._text == 0) {
            alert("검색결과가 없습니다.");
            this.bookResults = [];
            this.selectedBook = "default";
            return;
          }

          if (aladinXml.object.item.length == undefined) {
            this.bookResults.push({
              title: aladinXml.object.item.title._text,
              image: aladinXml.object.item.cover._text,
              author: aladinXml.object.item.author._text,
              priceStandard: aladinXml.object.item.priceStandard._text,
              category: aladinXml.object.item.categoryName._text,
              isbn: aladinXml.object.item.isbn13._text,
              itemID: aladinXml.object.item._attributes.itemId,
              priceSales: aladinXml.object.item.priceSales._text,
              pubDate: aladinXml.object.item.pubDate._text,
              point: aladinXml.object.item.mileage._text,
              publisher: aladinXml.object.item.publisher._text,
              salesPoint: aladinXml.object.item.salesPoint._text,
              description: aladinXml.object.item.description._text,
            });
          } else {
            if (aladinXml.object.item) {
              //검색 결과가 있으면
              this.bookResults = aladinXml.object.item.map((item) => ({
                //배열에 넣어줍니다.
                title: item.title._text,
                image: item.cover._text,
                author: item.author._text,
                priceStandard: item.priceStandard._text,
                category: item.categoryName._text,
                isbn: item.isbn13._text,
                itemID: item._attributes.itemId,
                priceSales: item.priceSales._text,
                pubDate: item.pubDate._text,
                point: item.mileage._text,
                publisher: item.publisher._text,
                salesPoint: item.salesPoint._text,
                description: item.description._text,
              }));
              this.selectedBook = "default"; //이건 왜 안될까요?
            } else {
              //검색결과가 없으면 선택창 작동안하게 합니다.
              this.bookResults = [];
              this.selectedBook = "default";
            }
          }
        })
        .catch((err) => {
          //에러처리
          console.log(err);
        });
    },
    onBookSelectionChange() {
      //select창에서 선택을 하게 되면 이 메소드가 실행됩니다.
      if (this.selectedBook === "default") {
        return;
      }

      if (this.selectedBook !== null) {
        const selectedBookData = this.bookResults[this.selectedBook]; //배열에서 select에서 선택한걸로 index를 받습니다.
        this.book_title = selectedBookData.title;
        this.book_author = selectedBookData.author;
        this.book_pricestandard = selectedBookData.priceStandard;
        this.book_categoryname = selectedBookData.category;
        this.book_pubdate = selectedBookData;
        this.book_pricesales = selectedBookData.priceSales;
        this.book_pubdate = selectedBookData.pubDate;
        this.book_point = selectedBookData.point;
        this.book_publisher = selectedBookData.publisher;
        this.book_salesPoint = selectedBookData.salesPoint;
        this.book_description = selectedBookData.description;
        if (selectedBookData.itemID) {
          //itemID을 이용하여 알라딘 api를 사용하여 책 이미지를 받습니다.
          axios({
            url: "aladin/naverBookImage",
            method: "POST",
            data: {
              isbn: selectedBookData.isbn,
              itemID: selectedBookData.itemID, //select로 선택한 것의 itemID을 서버로 넘깁니다. itemID는 알라딘의 고유 상품 번호입니다.
            },
          })
            .then((res) => {
              const aladinData = res.data;
              this.book_image = aladinData; //알라딘은 왤캐 이미지 해상도를 거지같이 주는거지
              this.book_provider = "naver";
            })
            .catch((err) => {
              axios({
                url: "aladin/aladinBookImage",
                method: "POST",
                data: {
                  itemID: selectedBookData.itemID,
                },
              })
                .then((res) => {
                  const aladinData = res.data;
                  this.book_image = aladinData.item[0].cover;
                  this.book_provider = "aladin";
                })
                .catch((err) => {
                  alert(err);
                });
            });
        } else {
          alert("이 에러가 왜나?"); //책 세트면 에러가 납니다..
        }
      }
    },
    insertBookInfo() {
      if (!this.book_title) {
        alert("책을 선택하세요");
        return;
      }
      axios({
        url: "aladin/insertInfo",
        method: "POST",
        data: {
          book_title: this.book_title,
          book_cover: this.book_image,
          book_author: this.book_author,
          book_pubdate: this.book_pubdate,
          // book_pricesales: this.book_pricesales,
          book_price: this.book_pricestandard,
          book_categoryname: this.book_categoryname,
          book_point: this.book_point,
          book_publisher: this.book_publisher,
          book_salesPoint: this.book_salesPoint,
          book_description: this.book_description,
        },
      })
        .then((res) => {
          if (res.status == 200) {
            alert(res.data);
            if (res.data === "ok") {
              this.$router.go(); // 등록하고나면 새로고침해서 넣은 데이터 안남아있게 수정했음!
            }
          }
        })
        .catch((err) => {
          alert(err.response.data);
        });
    },
  },
};
</script>

<style scoped></style>
