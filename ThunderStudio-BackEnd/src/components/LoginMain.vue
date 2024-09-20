<template>
  <div class="login-main-container">
    <div class="box">
      <div class="title">
        <span class="block"></span>
        <h1 class="text-3xl text-base-content">Thunder Studio<span></span></h1>
      </div>
      <div class="role">
        <div class="block"></div>
        <p class="text-lg text-base-content">雷霆工作室</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Lato:300,400|Poppins:300,400,800&display=swap');

.login-main-container {
  align-items: center;
  display: flex;
  height: 100%;
  justify-content: center;
  width: 100%;
}

.login-main-container .box {
  display: flex;
  flex-direction: column;
  height: 250px;
  justify-content: center;
  position: relative;
  width: 250px;
}

.login-main-container .box .title {
  align-items: center;
  display: flex;
  height: 50px;
  position: relative;
  width: 100%;
}

.login-main-container .box .title .block {
  animation: mainBlock 2s cubic-bezier(0.74, 0.06, 0.4, 0.92) forwards;
  background: #ffb510;
  display: flex;
  height: inherit;
  position: absolute;
  width: 0;
}

.login-main-container .box .title h1 {
  align-items: baseline;
  -o-animation: mainFadeIn 2s forwards;
  -webkit-animation: mainFadeIn 2s forwards;
  animation: mainFadeIn 2s forwards;
  animation-delay: 1.6s;
  display: flex;
  font-family: 'Poppins';
  opacity: 0;
  position: relative;
}

.login-main-container .box .title h1 span {
  -webkit-animation: load 0.6s cubic-bezier(0.74, 0.06, 0.4, 0.92) forwards;
  animation: popIn 0.8s cubic-bezier(0.74, 0.06, 0.4, 0.92) forwards;
  animation-delay: 2s;
  background: #ffb510;
  -moz-border-radius: 50%;
  -webkit-border-radius: 50%;
  border-radius: 50%;
  bottom: 13px;
  height: 0;
  margin-left: 5px;
  margin-top: -10px;
  position: absolute;
  right: -12px;
  width: 0;
}

.login-main-container .box .role {
  align-items: center;
  display: flex;
  height: 30px;
  margin-top: -10px;
  position: relative;
  width: 100%;
}

.login-main-container .box .role .block {
  animation: secBlock 2s cubic-bezier(0.74, 0.06, 0.4, 0.92) forwards;
  animation-delay: 2s;
  background: #e91e63;
  display: flex;
  height: inherit;
  position: absolute;
  width: 0;
}

.login-main-container .box .role p {
  animation: secFadeIn 2s forwards;
  animation-delay: 3.2s;
  font-family: 'Lato';
  font-weight: 400;
  letter-spacing: 5px;
  opacity: 0;
  text-transform: uppercase;
}

@keyframes mainBlock {
  0% {
    width: 0;
    left: 0;
  }
  50% {
    width: 100%;
    left: 0;
  }
  100% {
    width: 0;
    left: 100%;
  }
}

@keyframes secBlock {
  0% {
    width: 0;
    left: 0;
  }
  50% {
    width: 100%;
    left: 0;
  }
  100% {
    width: 0;
    left: 100%;
  }
}

@keyframes mainFadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

@keyframes popIn {
  0% {
    width: 0;
    height: 0;
    background: #e9d856;
    border: 0 solid #ddd;
    opacity: 0;
  }
  50% {
    width: 10px;
    height: 10px;
    background: #e9d856;
    opacity: 1;
    bottom: 45px;
  }
  65% {
    width: 7px;
    height: 7px;
    bottom: 0;
    width: 15px;
  }
  80% {
    width: 10px;
    height: 10px;
    bottom: 20px;
  }
  100% {
    width: 7px;
    height: 7px;
    background: #e9d856;
    border: 0px solid #222;
    bottom: 13px;
  }
}

@keyframes secFadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 0.5;
  }
}

footer {
  align-items: center;
  animation: top 0.8s forwards;
  animation-delay: 4s;
  background: #ffb510;
  bottom: -80px;
  display: flex;
  height: 80px;
  justify-content: center;
  position: absolute;
  right: 0;
  width: 350px;
}

footer span {
  align-items: center;
  color: #232323;
  display: flex;
  font-family: 'Poppins';
  font-size: 12px;
  justify-content: center;
}

footer span i {
  animation: icon 2s forwards;
  animation-delay: 4s;
  color: #232323;
  font-size: 22px;
  margin-right: 25px;
  opacity: 0;
}

@keyframes top {
  0% {
    opacity: 0;
    bottom: -80px;
  }
  100% {
    opacity: 1;
    bottom: 0;
  }
}

@keyframes icon {
  0% {
    opacity: 0;
    transform: scale(0);
  }
  50% {
    opacity: 1;
    transform: scale(1.3) rotate(-2deg);
  }
  100% {
    opacity: 1;
    bottom: 0;
  }
}
</style>
