import { Navigate, Outlet, useLocation } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const AuthRoute = () => {
  const { isAuthenticated } = useAuth();
  const location = useLocation();

  if (!isAuthenticated) {
    localStorage.setItem("redirectPath", location.pathname);
    return <Navigate to="/login" replace />;
  }

  return <Outlet />;
};

export default AuthRoute;
