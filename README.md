# Walk or Run 🏃‍♂️

## 프로젝트 소개
삼성 헬스 데이터 내보내기를 활용한 러닝 데이터 대시보드 및 커뮤니티 기능을 제공하는 백엔드 REST API 서버입니다.

## 팀원
<table>
  <tr>
    <td align="center">
      <img src="https://github.com/clapsheep.png" width="100" height="100" style="border-radius: 50%;"/><br />
      <b>clapsheep</b><br />
      <a href="https://github.com/clapsheep">
        <img src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=github" />
      </a>
    </td>
    <td align="center">
      <img src="https://github.com/rpeowiqu.png" width="100" height="100" style="border-radius: 50%;"/><br />
      <b>rpeowiqu</b><br />
      <a href="https://github.com/rpeowiqu">
        <img src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=github" />
      </a>
    </td>
    <td align="center">
      <img src="https://github.com/zyu22.png" width="100" height="100" style="border-radius: 50%;"/><br />
      <b>zyu22</b><br />
      <a href="https://github.com/zyu22">
        <img src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=github" />
      </a>
    </td>
    <td align="center">
      <img src="https://github.com/yujeong789.png" width="100" height="100" style="border-radius: 50%;"/><br />
      <b>yujeong789</b><br />
      <a href="https://github.com/yujeong789">
        <img src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=github" />
      </a>
    </td>
  </tr>
</table>

## 기술 스택
| Category | Technologies |
|----------|-------------|
| Backend | ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white) |
| Database | ![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white) <br> ![MyBatis](https://img.shields.io/badge/MyBatis-3776AB?style=flat-square&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyNCAyNCI+PHBhdGggZmlsbD0iI2ZmZiIgZD0iTTEyIDJDNi40NzcgMiAyIDYuNDc3IDIgMTJzNC40NzcgMTAgMTAgMTAgMTAtNC40NzcgMTAtMTBTMTcuNTIzIDIgMTIgMnptLS4zNDMgMTQuNzA2aC0uODhWNy4yOTRoLjg4djkuNDEyeiIvPjwvc3ZnPg==) |
| DevOps | ![AWS](https://img.shields.io/badge/AWS-FF9900?style=flat-square&logo=amazonwebservices&logoColor=white) <br> ![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white) <br> ![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=flat-square&logo=githubactions&logoColor=white) |

## 데이터베이스 스키마

### 사용자 관련 테이블

| 테이블명 | 설명 | 주요 컬럼 |
|:---------|:-----|:----------|
| Users | 사용자 정보 | user_id(PK), user_email, user_name, user_nickname |
| Password_Question | 비밀번호 찾기 질문 | question_id(PK), question_description |
| Password_Answers | 비밀번호 답변 | password_answer_id(PK), user_id(FK), question_id(FK) |
| Tokens | 인증 토큰 | token_id(PK), user_id(FK), access_token, refresh_token |

### 챌린지 관련 테이블

| 테이블명 | 설명 | 주요 컬럼 |
|:---------|:-----|:----------|
| Challenges | 챌린지 정보 | challenge_id(PK), challenge_title, challenge_description |
| Challenge_Categories | 챌린지 카테고리 | challenge_category_code(PK), challenge_category_name |
| Challenge_Participants | 챌린지 참여자 | participant_id(PK), challenge_id(FK), user_id(FK) |
| Comments | 챌린지 댓글 | comment_id(PK), challenge_id(FK), comment_content |

### 운동 데이터 테이블

| 테이블명 | 설명 | 주요 컬럼 |
|:---------|:-----|:----------|
| Time | 운동 시간 정보 | time_id(PK), user_id(FK), start_time, end_time |
| Calorie | 소모 칼로리 | calorie_id(PK), time_id(FK), total_calorie |
| HeartRate | 심박수 정보 | heart_rate_id(PK), time_id(FK), max/min/mean_heart_rate |
| Distance | 이동 거리 | distance_id(PK), time_id(FK), distance |
| Steps | 걸음 수 | steps_id(PK), time_id(FK), step_count |
| Speed | 속도 정보 | speed_id(PK), time_id(FK), mean_speed, max_speed |

## API 문서 📚
Spring Docs를 통해 API 문서를 제공할 예정입니다. (준비중)

## 진행 상황 ⏳
- [x] 프로젝트 초기 설정
- [x] 기술 스택 선정
- [ ] API 문서 작성
- [ ] 백엔드 서버 개발
- [ ] 프론트엔드 개발 (예정)

> 자세한 기능 명세와 프로젝트 설명은 추후 업데이트될 예정입니다.
