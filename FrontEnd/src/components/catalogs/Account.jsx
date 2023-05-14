import { useEffect, useState, useRef } from "react";
import { useNavigate } from 'react-router-dom';
import News from './NewsForAccount';
const hostURL = "http://localhost:8080";
const host = hostURL + "/api/1.0";

const Account = function () {

    const [currentUser, setCurrentUser] = useState({});

    const loginInput = useRef();
    const emailInput = useRef();
    const navigate = useNavigate();

    useEffect(() => {
        getUser().then(user => setCurrentUser(user));
    }, []);

    const getTokenForHeader = function () {
        return "Bearer " + localStorage.getItem("token");
    }

    const login = async function () {
        const requestParams = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(currentUser),
        };
        const requestUrl = hostURL + "/jwt/login";
        const response = await fetch(requestUrl, requestParams);
        const result = await response.text();
        if (response.status === 200) {
            localStorage.setItem("token", result);
            localStorage.setItem("user", currentUser.login);
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
    }

    const updateUser = async function () {
        const requestParams = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": getTokenForHeader(),
            },
            body: JSON.stringify(currentUser),
        };
        const requestUrl = host + `/user`;
        const response = await fetch(requestUrl, requestParams);
        const result = await response.text();
        return result;
    }
    
    const getUser = async function () {
        const requestParams = {
            method: "GET",
            headers: {
                "Authorization": getTokenForHeader(),
            }
        };
        let login = localStorage.getItem("user");
        const requestUrl = host + `/user?login=${login}`;
        const response = await fetch(requestUrl, requestParams);
        const user = await response.json();
        return user;
    }
    

    const onSubmit = function (event) {
        event.preventDefault();
        updateUser().then((result) => {
            alert(result);
            if (result === "Profile updated") {
                login();
            }
        });
    }

    const onInput = function (event, fieldName) {
        setCurrentUser(oldUser => ({
            ...oldUser, [fieldName]: event.target.value
        }));
    }

    const logoutButtonOnClick = function () {
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        localStorage.removeItem("role");
        window.dispatchEvent(new Event("storage"));
        navigate("/login");
    }

    return (
        <>
            <div className="border-bottom pb-3 mb-3">
                <button className="btn btn-primary"
                        onClick={logoutButtonOnClick}>
                    Log Out
                </button>
            </div>
            <h4 className="mb-4">Update profile</h4>
            <form onSubmit={onSubmit}>
                <div className="mb-3">
                    <p className="mb-1">New Login</p>
                    <input className="form-control" type="text" required
                           ref={loginInput} value={currentUser.login}
                           onInput={(event) => onInput(event, "login")} />
                </div>
                <div className="mb-3">
                    <p className="mb-1">New Email</p>
                    <input className="form-control" type="text" required
                           ref={emailInput} value={currentUser.email}
                           onInput={(event) => onInput(event, "email")} />
                </div>
                <div className="mb-3">
                    <p className="mb-1">Enter password</p>
                    <input className="form-control" type="password" required
                           ref={emailInput}
                           onInput={(event) => onInput(event, "password")} />
                </div>
                <button type="submit" className="btn btn-primary">
                    Save
                </button>
            </form>
            <News>
            </News>
        </>
    )
}

export default Account;