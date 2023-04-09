import { useEffect } from "react";
import { useState } from "react";
import Banner from '../common/Banner'

export default function Catalogs(props) {
    return (
        <div className="container-fluid">
        <Banner />
        <div className="lp"><img className="img-fluid img-school float-start my-2" src="img/Shkola.jpg" alt="shkola"/>
            <div>
            <p className="fs-4">Мы расположены по адресу: 423228, г. Нижневратовск, ул. Корзинова, д. 37</p>
            <div>
                <div className="fs-4">Контактные телефоны
                <p>(8228) 13-77-85</p>
                <p>(8800) 55-53-50</p>
                <p>факс (8911) 22-44-96</p>
                </div>
            </div>
            </div>
        </div><a className="fs-4" href="mailto:Nizhnev.school10@mail.ru">email: Nizhnev.school10@mail.ru</a>
        </div>
    );
}