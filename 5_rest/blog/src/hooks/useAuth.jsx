import { useContext } from "react";
import { LoginContext } from "../contexts/LoginContext";

// useAuth에 null인 정보를 관리하게 함
export const useAuth = () => {
    return useContext(LoginContext);
}