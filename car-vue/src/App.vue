<template>
  <div id="app">
    <router-view />
  </div>
</template>

<style lang="scss">
@charset "utf-8";
/* 初始化 */
body,
ul,
li,
ol,
h1,
h2,
h3,
h4,
h5,
h6,
input,
textarea,
select,
p,
dl,
dt,
dd,
a,
img,
button,
form,
table,
th,
tr,
td,
tbody,
article,
aside,
details,
figcaption,
figure,
footer,
header,
hgroup,
menu,
nav,
section {
  margin: 0;
  padding: 0;
}
body {
  overflow-x: hidden;
  font: normal 14px/1.5 "Microsoft Yahei", Tahoma, "Lucida Grande", Verdana,
    STXihei, hei;
}
em,
i {
  font-style: normal;
}
strong {
  font-weight: normal;
}
a {
  text-decoration: none;
  color: #333;
}
ul,
ol {
  list-style: none;
}
h1,
h2,
h3,
h4,
h5,
h6 {
  font-size: 100%;
  font-weight: normal;
}
/*专门清除浮动的 */
.clearfix:after {
  content: "";
  display: block;
  visibility: hidden;
  height: 0;
  clear: both;
}
#app {
  border: none;
}
.fade-leave-active,
.fade-enter-active {
    transition: opacity 2s;
}
</style>
	  