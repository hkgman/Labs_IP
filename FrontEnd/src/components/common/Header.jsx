import { NavLink } from 'react-router-dom';

export default function Header(props) {
    return (
        <header className="fs-4 fw-bold p-1 text-white bg-primary bg-gradient">
            <div><img className="img-fluid float-start" src="../img/Emblema.png" alt="Emblema"/>
                <p className="fs-5 Cont">Муниципальное бюджетное общеобразовательное учреждение средняя общеобразовательная школа №10</p>
            </div>
            <nav className="navbar navbar-expand-md navbar-dark">
                <div className="container-fluid">
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation"> <span className="navbar-toggler-icon"></span></button>
                    <div className="navbar-collapse collapse justify-content-end" id="navbarNav">
                        <nav className="headers-problem navbar navbar-expand-lg d-flex">
                            {
                                props.links.map(route =>
                                    <button key={route.path}
                                        className="btn btn-outline-light mx-1">
                                        <NavLink className="nav-link" to={route.path}>
                                                {route.label}
                                        </NavLink>
                                    </button>
                                )
                            }
                        </nav>
                    </div>
                </div>
            </nav>
        </header>
    );
}