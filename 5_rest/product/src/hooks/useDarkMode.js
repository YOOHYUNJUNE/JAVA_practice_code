import { useEffect, useState } from "react";

export const useDarkMode = () => {

    const [isDarkMode, setIsDarkMode] = useState(() => {
        const savedMode = localStorage.getItem('darkMode');
        return savedMode === "true" ? true : false;
    });

    const toggleDarkMode = () => setIsDarkMode(mode => !mode);

    useEffect(() => {
        localStorage.setItem('darkMode', isDarkMode);
    }, [isDarkMode]);

    return { isDarkMode, toggleDarkMode }
}