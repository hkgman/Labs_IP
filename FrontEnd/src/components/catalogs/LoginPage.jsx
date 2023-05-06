import { useState, useEffect } from "react";
import { Link, useNavigate } from 'react-router-dom';
import { useRef } from "react";

const hostURL = "http://localhost:8080";

const LoginPage = function () {

    const loginInput = useRef();
    const passwordInput = useRef();
    const navigate = useNavigate();

    useEffect(() => {
    }, []);

    const login = async function (login, password) {
        const requestParams = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({login: login, password: password}),
        };
        const response = await fetch(hostURL + "/jwt/login", requestParams);
        const result = await response.text();
        if (response.status === 200) {
            localStorage.setItem("token", result);
            localStorage.setItem("user", login);
            getRole(result);
        } else {
            localStorage.removeItem("token");
            localStorage.removeItem("user");
            localStorage.removeItem("role");
        }
    }

    const getRole = async function (token) {
        const requestParams = {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        };
        const requestUrl = hostURL + `/who_am_i?token=${token}`;
        const response = await fetch(requestUrl, requestParams);
        const result = await response.text();
        localStorage.setItem("role", result);
        window.dispatchEvent(new Event("storage"));
        navigate("/main");
    }

    const loginFormOnSubmit = function (event) {
        event.preventDefault();
        login(loginInput.current.value, passwordInput.current.value);
    };

    return (
        <>
            <div className="mx-auto my-3">
                <form onSubmit={(event) => loginFormOnSubmit(event)}>
                    <div className="mb-3">
                        <p className="mb-1">Login</p>
                        <input className="form-control"
                               type="text" required autoFocus
                               ref={loginInput} />
                    </div>
                    <div className="mb-3">
                        <p className="mb-1">Password</p>
                        <input className="form-control"
                               type="password" required
                               ref={passwordInput} />
                    </div>
                    <div className="mb-3">
                        <button type="submit" className="btn btn-success">
                            Sing in
                        </button>
                    </div>
                    <div>
                        <p>
                            <span>Not a member yet?&nbsp;</span>
                            <Link to="/signup">Sign Up here</Link>
                        </p>
                    </div>
                </form>
            </div>
        </>
    )
}

export default LoginPage;