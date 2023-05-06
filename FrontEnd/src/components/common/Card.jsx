import { useEffect, useState } from 'react';
import ModalEdit from './ModalComment';
import TablePostAndComment from './TablePostAndComment';
export default function Card(props) {
    
    

    return (
        <div className="container-fluid mt-3">
            {
                props.items.map((item, index) =>
                    <div key={item.id}
                        className="card border-dark mb-3 d-flex flex-row text-black justify-content-between">
                        <div className=''>
                            <img className="col" style={{width: "300px" ,height: "200px" }} src={item.image}/>
                        </div>
                        <div className='d-flex flex-grow-1 flex-column'>
                            <div className='flex-grow-1 mx-2'>
                                {
                                    props.headers.map(header =>
                                        header.name == "image" ? null :
                                            <div className="" key={item.id + header.name}>{item[header.name]}</div>
                                    )
                                }
                            </div>
                            <div className="d-flex flex-row justify-content-end ">
                                <a href={`/Post?id=${item.id}`} className='btn btn-outline-primary mx-2'><i className="fa-solid fa-envelopes-bulk"></i></a>   
                            </div>
                            <TablePostAndComment
                                comments={item.comments}
                                value={props.value}
                            />
                        </div>
                    </div>
                )
            }
        </div>
    );
}