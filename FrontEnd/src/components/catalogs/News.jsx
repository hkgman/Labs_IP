import { useState, useEffect } from 'react';
import Catalog from './Catalog';
import New from '../../models/NewDto';

export default function News(props) {
    const url = 'post/';
    const transformer = (data) => new New(data);
    const catalogStudHeaders = [
        { name: 'image', label: 'Картинка' },
        { name: 'heading', label: 'Заголовок' },
        { name: 'content', label: 'Новости' },
    ];

    const [data, setData] = useState(new New());
    const fileReader=new FileReader();
    fileReader.onloadend=()=>{
      const tempval=fileReader.result; 
      setData({ ...data, ['image']: tempval})
    }
    const handleOnChange=(event)=>{
      event.preventDefault();
      const file=event.target.files[0];
      fileReader.readAsDataURL(file);
    };

    function handleOnAdd() {
      setData(new New());
    }

    function handleOnEdit(data) {
        console.log(data);
        setData(new New(data));
    }
    function handleFormChange(event) {
      setData({ ...data, [event.target.id]: event.target.value })
    }

    return (
        <Catalog 
            headers={catalogStudHeaders} 
            url={url}
            transformer={transformer}
            data={data}
            onAdd={handleOnAdd}
            onEdit={handleOnEdit}>
            <div className="mb-3 text-black">
                <label htmlFor="image" className="form-label">Изображение</label>
                <input type="file" id="image" className="form-control" required onChange={handleOnChange}/>
            </div>
            <div className="mb-3">
                <label htmlFor="heading" className="form-label text-black">Заголовок</label>
                <input type="text" id="heading" className="form-control" required
                    value={data.heading} onChange={handleFormChange}/>
            </div>
            <div className="mb-3">
                <label htmlFor="content" className="form-label text-black">Содержание</label>
                <input type="text" id="content" className="form-control" required 
                    value={data.content} onChange={handleFormChange}/>
            </div>
        </Catalog>
    );
}