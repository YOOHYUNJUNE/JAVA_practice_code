import { useEffect, useState } from "react";
import { useAuth } from "../../hooks/useAuth";
import Calendar from "react-calendar";
import 'react-calendar/dist/Calendar.css';  // 기본 스타일 적용
import moment from "moment";
import styled from 'styled-components';


const StyledCalendarWrapper = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
  position: relative;
  .react-calendar {
    width: 100%;
    border: none;
    border-radius: 0.5rem;
    box-shadow: 4px 2px 10px 0px rgba(0, 0, 0, 0.13);
    padding: 3% 5%;
    background-color: white;
  }`


const WeekSummaryWrapper = styled.div`
  width: 30%;
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #f9f9f9;
  border-left: 1px solid #ccc;
  box-shadow: -4px 2px 10px 0px rgba(0, 0, 0, 0.13);
`;



const StyledCalendar = styled(Calendar)``;

const Home = () => {
    const { userInfo } = useAuth();

    // 선택된 날짜를 저장하는 상태
    const [selectedDate, setSelectedDate] = useState(null);

    // 날짜 클릭 시 동작하는 함수
    const handleDateClick = (date) => {
        // 같은 날짜를 클릭하면 URI로 이동
        if (selectedDate && selectedDate.getTime() === date.getTime()) {
        window.location.href = `/some-uri/${date.toISOString().split('T')[0]}`;
        } else {
        // 날짜 선택 시 테두리 표시
        setSelectedDate(date);
        }
    };





    return (
        <>
        <h1>홈</h1>
        {
            userInfo && userInfo.email ?
            <>
                로그인 유저 정보 : {userInfo.email}, {userInfo.role}
            </>
            :
            <>
                로그인합시다
            </>
        }

<StyledCalendarWrapper>
<Calendar
        calendarType="gregory" // 일요일을 한 주의 시작으로 설정
        onClickDay={handleDateClick}
        formatDay={(locale, date) => moment(date).format("D")} // 일 제거 숫자만 보이게
        formatMonthYear={(locale, date) => moment(date).format("YYYY. MM")} // 네비게이션에서 2023. 12 이렇게 보이도록 설정
        // formatYear={(locale, date) => moment(date).format("YYYY")} // 네비게이션 눌렀을때 숫자 년도만 보이게
        next2Label={null} // +1년 & +10년 이동 버튼 숨기기
        prev2Label={null} // -1년 & -10년 이동 버튼 숨기기
        minDetail="year" // 10년단위 년도 숨기기
      />
      <style jsx>{`
        .selected-tile {
          border: 2px solid #ff5722;  // 선택된 날짜에 테두리 스타일 추가
          border-radius: 50%;  // 둥근 테두리
        }
      `}</style>
    </StyledCalendarWrapper>


        </>

    );




    
}




 
export default Home;