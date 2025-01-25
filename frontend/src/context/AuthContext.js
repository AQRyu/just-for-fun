import { createContext, useContext, useState } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(JSON.parse(localStorage.getItem("user")));
  const [isAuthenticated, setIsAuthenticated] = useState(
    !!localStorage.getItem("user")
  );
  const token = user?.jwt;

  const handleLogin = (user) => {
    localStorage.setItem("user", JSON.stringify(user));
    setUser(user);
    setIsAuthenticated(true);
  };

  const handleLogout = () => {
    debugger;
    localStorage.removeItem("user");
    setUser(null);
    setIsAuthenticated(false);
  };

  const contextValue = {
    isAuthenticated,
    user,
    token,
    handleLogin,
    handleLogout,
  };

  return (
    <AuthContext.Provider value={contextValue}>{children}</AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === null) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};
