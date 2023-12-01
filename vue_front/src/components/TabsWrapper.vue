<script setup>
import { useSlots, ref, provide, onMounted, watch } from "vue";

const slots = useSlots();
const tabTitles = ref(slots.default().map((tab) => tab.props.title));
const selectedTitle = ref(tabTitles.value[0]);

onMounted(() => {
    //실행될때
    const tapName = sessionStorage.getItem("eventTap");
    //세션스토리지에서 데이터 받아오기
    if (tapName && tabTitles.value.includes(tapName)) {
        //세션스토리지에 데이터가 있거나 배열에 포함되어있으면
        selectedTitle.value = tapName;
        //선택 탭을 세션스토리지 데이터로 설정
    } else {
        selectedTitle.value = tabTitles.value[0];
        //없으면 첫번째 탭으로 설정
        sessionStorage.setItem("eventTap", tabTitles.value[0]);
        //세션스토리지 데이터도 다시 설정
    }
});

const handleTabClick = (title) => {
    selectedTitle.value = title;
    //탭 선택에 데이터 넣은뒤
    sessionStorage.setItem("eventTap", title);
    // 선택한 탭을 sessionStorage에 저장
};

provide("selectedTitle", selectedTitle);
</script>

<template>
    <div class="tabs">
        <ul class="tabs__header">
            <li
                v-for="title in tabTitles"
                :key="title"
                class="tabs__item"
                :class="{ selected: selectedTitle === title }"
                @click="handleTabClick(title)"
            >
                {{ title }}
            </li>
        </ul>

        <slot />
    </div>
</template>
