import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";

createApp(App).use(router).mount("#app");

window.Kakao.init("be3591e5eb435becf923984f4b447ac2");
