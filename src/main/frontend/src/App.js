import logo from './logo.svg';
import './App.css';
import {Login} from "./pages/Accountpage/Login";
import {Main} from "./pages/Mainpage/Main";
import {Input} from "./pages/Documentpage/Input";
import {Join} from "./pages/Accountpage/Join";
import {Showlist} from "./pages/Documentpage/Showlist";
import {Showdetail} from "./pages/Documentpage/Showdetail";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

function App() {
  return (
      <Router>
        <Routes>
          {/* 메인 페이지(기본경로) */}
          <Route path="/" element={<Main />} />

          {/* 로그인 페이지 경로 설정 */}
          <Route path="/login" element={<Login />} />

          {/* 회원가입 경로 설정 */}
          <Route path="/join" element={<Join />} />

          {/* 계출서 입력 경로 설정 */}
          <Route path="/input" element={<Input />} />

          {/* 계출서 리스트 경로 설정 */}
          <Route path="/showlist" element={<Showlist />} />

          {/* 계출서 상세 */}
          <Route path="/show" element={<Showdetail />} />




        </Routes>
      </Router>
  );
}

export default App;
