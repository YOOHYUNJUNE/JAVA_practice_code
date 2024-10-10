import React, { useState } from 'react';


// 현재 X월 X주차를 표시하는 함수


const getWeekNumberInMonth = (date) => {
    const firstDayOfMonth = new Date(date.getFullYear(), date.getMonth(), 1);
    const dayOfWeek = firstDayOfMonth.getDay(); // 그 달의 첫 번째 요일
    const adjustedDate = date.getDate() + dayOfWeek; // 첫 번째 요일에 따른 날짜 조정
    return Math.ceil(adjustedDate / 7); // 월 기준 주차 계산
  };
  

// 교차 월 주차 표시 함수
const getWeekLabel = (startDate, endDate, currentMonth) => {
  const startMonth = startDate.getMonth() + 1;
  const endMonth = endDate.getMonth() + 1;

  const startWeek = getWeekNumberInMonth(startDate);
  const endWeek = getWeekNumberInMonth(endDate);

  if (startMonth !== endMonth) {
    if (currentMonth === startMonth) {
      return `${startMonth}월 ${startWeek}주차 (${endMonth}월 ${endWeek}주차)`;
    } else {
      return `${endMonth}월 ${endWeek}주차 (${startMonth}월 ${startWeek}주차)`;
    }
  }
  return `${startMonth}월 ${startWeek}주차`;
};

// 주차 컴포넌트
const WeekDisplay = ({ currentDate, currentMonth }) => {
  const startOfWeek = new Date(currentDate);
  startOfWeek.setDate(currentDate.getDate() - currentDate.getDay()); // 주 시작일 (일요일)

  const endOfWeek = new Date(startOfWeek);
  endOfWeek.setDate(startOfWeek.getDate() + 6); // 주 종료일 (토요일)

  const weekLabel = getWeekLabel(startOfWeek, endOfWeek, currentMonth);

  return (
    <div>
      <h3>현재 주차: {weekLabel}</h3>
      <p></p>
      <p></p>
      <p></p>
    </div>
  );
};

// 메인 컴포넌트
const Test = () => {
  const [currentDate, setCurrentDate] = useState(new Date());
  const [currentMonth, setCurrentMonth] = useState(currentDate.getMonth() + 1);

  return (
    <div>
      <h1>Test</h1>
      <WeekDisplay currentDate={currentDate} currentMonth={currentMonth} />
    </div>
  );
};

export default Test;

