import { useState, useEffect } from 'react';
import '../../style.css'
export default function Banner(props) {
    const[bannerNum,setBanner]=useState(['show','hide','hide']);
    useEffect(() => {
        const length=3;
        var old = length-1;
        var current = 0;
        const timer = window.setInterval(()=>{
            setBanner([...bannerNum, bannerNum[current] = 'show', bannerNum[old] = 'hide']);
            old = current;
            current++;
            if (current ===length) {
                current = 0;
            }
        },5000);
        return()=>{
            window.clearInterval(timer);
        }
    },[]);
    return(
        <div id="banner"><img className={bannerNum[0]} src="img/banner1.png"/><img className={bannerNum[1]} src="img/banner2.png"/><img className={bannerNum[2]} src="img/banner3.png"/></div>
    );
}