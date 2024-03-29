<template>
  <div class="college-management-system">
    <header>
      <h1>校内车辆出入管理系统</h1>
      <img src="./f31e6b6d-4e6f-4e1d-936e-865306ce5f30.jpg" alt="校内车辆出入管理系统图片" class="header-image">
    </header>
    <main align = 'center'>
      <p>欢迎使用校内车辆出入管理系统 </p>
    </main>
  </div>
</template>

<script>
export default {
}
</script>

<style scoped>
.college-management-system {
  font-family: 'Arial', sans-serif;
  width: 100%;
  max-width: 1000px;
  margin: 0 auto;
  padding: 20px;
  box-sizing: border-box;
}

header {
  text-align: center;
  margin-bottom: 30px;
}

header h1 {
  font-size: 32px;
  color: #333;
  margin-bottom: 10px;
}

.header-image {
  width: 200%;
  max-width: 1000px;
  height: auto;
  display: block;
  margin: 0 auto;
}

main {
  line-height: 1.6;
  color: #666;
}

/* 响应式布局 */
@media (max-width: 600px) {
  .college-management-system {
    padding: 10px;
  }
  header h1 {
    font-size: 24px;
  }
}
</style>