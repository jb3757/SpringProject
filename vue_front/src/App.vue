<template>
    <nav class="main">
        <router-view :key="$route.fullPath" />
    </nav>
</template>

<script>
import CSS from "../src/assets/css/reset.css";

export default {
    name: "App",
    components: {
        CSS,
    },
};
</script>

<style>
a {
    cursor: pointer;
    text-decoration: none;
}
button {
    cursor: pointer;
}
input[type="submit"] {
    cursor: pointer;
}
</style>
