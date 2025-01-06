import { useCallback, useState } from "react";
import { useNavigate } from "react-router-dom";

const useAuthentication = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(
    !!localStorage.getItem("user")
  );
  const navigate = useNavigate();

  const handleLogin = (token) => {
    localStorage.setItem("user", token);
    setIsAuthenticated(true);
    navigate("/");
  };

  const handleLogout = useCallback(() => {
    localStorage.removeItem("user");
    setIsAuthenticated(false);
    navigate("/");
  }, [navigate]);

  return { isAuthenticated, handleLogin, handleLogout };
};

export default useAuthentication;
