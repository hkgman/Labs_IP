import { useState } from 'react';
import {useEffect} from 'react';

export default function Toolbar(props) {
    function add() {
        props.onAdd();
    }
    return (
        <div className="d-flex float-start my-2">
            <div>
                <button type="button" className="btn btn-primary" onClick={add}>
                    +
                </button>
            </div>
            
        </div >
    );
}