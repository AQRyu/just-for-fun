import axios from "axios";

const api = axios.create({
  baseURL: process.env.REACT_APP_BACKEND_URL,
  timeout: 10000,
});

const getToken = () => {
  return JSON.parse(localStorage.getItem("user"))?.jwt;
};

api.interceptors.request.use(
  (config) => {
    const token = getToken();
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    console.error("Request interceptor error:", error);
    return Promise.reject(error);
  }
);

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      console.error("Unauthorized. Logging out.");
    } else if (error.response) {
      console.error("Server responded with an error:", error.response.data);
    } else if (error.request) {
      console.error("No response received from the server:", error.request);
    } else {
      console.error("Error setting up the request:", error.message);
    }

    return Promise.reject(error);
  }
);

export default api;
