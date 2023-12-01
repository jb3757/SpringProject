export const isLogindDisplay = {
    data() {
        return {
            //로그인여부
            logined: false,
        }
    },
    created() {
        this.isLogined()
    },
    mounted() {
        this.blurToggle()
    },
    methods: {
        isLogined() {
      //  some 메서드는 배열의 요소 중 하나라도 특정 조건을 만족하는지 확인

      // localStorage에 저장된 모든 키의 목록을 가져오는 함수
      function getAllKeysFromLocalStorage() {
        return Array.from({ length: localStorage.length }, (_, index) => localStorage.key(index));
      }
      // 특정 키가 있는지 확인하는 함수
      function isKeyInLocalStorage(keyToFind) {
        const allKeys = getAllKeysFromLocalStorage();
        return allKeys.some(key => key === keyToFind);
      }

      const userIDKey = 'userID';
      const isUserIDPresent = isKeyInLocalStorage(userIDKey);

      if (isUserIDPresent) {
        this.logined = true;
      } else {
        this.logined = false;
      }
    },
    blurToggle() {
      if(this.logined == true) {
        this.$refs.mainBlur.classList.remove("blur");
        console.log("로그인상태");
      } else {
        this.$refs.mainBlur.classList.add("blur");
        console.log("비로그인상태");
      }
    },
    }
}