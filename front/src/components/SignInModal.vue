<template>
  <v-row
    justify="center">
    <v-dialog
      v-model="dialog"
      :fullscreen="fullScreen"
      persistent>
      <v-card 
        class="d-flex flex-column align-center py-5">
        <v-spacer />
        <v-img
          width="300px"
          :src="logo"
          alt="logo" />
        <v-card-text
          :class="formWidth">
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  v-model="email"
                  label="Email(@11stcorp.com)*"
                  required />
              </v-col>
              <v-col
                v-show="isSignUp"
                cols="12">
                <v-text-field
                  v-model="nickname"
                  label="Nickname*"
                  type="text"
                  required />
              </v-col>
              <v-col cols="12">
                <v-text-field
                  v-model="password"
                  label="Password*"
                  type="password"
                  required />
              </v-col>
              <v-col
                v-show="isSignUp"
                cols="12">
                <v-text-field
                  v-model="confirmPassword"
                  label="Confirm Password*"
                  type="password"
                  required />
              </v-col>
            </v-row>
          </v-container>
          <small>*indicates required field</small>
        </v-card-text>
        <v-card-actions>
          <v-btn
            color="grey-darken-1"
            text-color="white"
            text
            @click="switchMode">
            {{ !isSignUp ? '회원가입' : '로그인' }}
          </v-btn>
          <v-btn
            :color="primaryColor"
            text-color="white"
            text
            @click="requestSign">
            {{ isSignUp ? '가입' : '로그인' }}
          </v-btn>
        </v-card-actions>
        <v-spacer />
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
  import logo from '../assets/logo_variation_black.svg';

  const DIALOG_WIDTH_THRESHOLD = 959.98;

  export default {
    name: 'SignInModal',
    data () {
      return {
        isSignUp: false,
        primaryColor: '#F43142',
        dialog: false,
        fullScreen: false,
        logo,
        email: '',
        nickname: '',
        password: '',
        confirmPassword: '',
      };
    },
    computed: {
      formWidth(){
        return this.fullScreen ? 'width-80percent' : 'min-width-550';
      },
    },
    created() {
      // @TODO: token 확인. 로그인 안 되었을 경우 로그인 모달창 띄우기
      setTimeout(() => {
        // this.dialog = true;
        this.defineFullScreen();
      }, 100);
    },
    mounted() {
      window.addEventListener('resize', this.defineFullScreen);
    },
    beforeUnmount() {
      window.removeEventListener('resize', this.defineFullScreen);
    },
    methods: {
      defineFullScreen() {
        this.fullScreen = window.innerWidth < DIALOG_WIDTH_THRESHOLD;
      },
      switchMode() {
        this.isSignUp = !this.isSignUp;
        this.resetInput();
      },
      requestSign() {
        this.isSignUp ? this.requestSignUp() : this.requestSignIn();
      },
      resetInput() {
        this.email = '';
        this.nickname = '';
        this.password = '';
        this.confirmPassword = '';
      },
      requestSignUp() {
        // @TODO: Sign Up API 요청
      },
      requestSignIn() {
        // @TODO: Sign In API 요청
      },
    },
  };
</script>

<style lang="scss" scoped>
.width-80percent {
  width: 80%;
}
.min-width-550 {
  width: 550px;
}
</style>
