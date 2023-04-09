import { React } from 'react'
import classes from '../../ModalEdit.module.css'

export default function ModalEdit({children, visible, setVisible}) {

    const rootClasses = [classes.myModal]
    if (visible) {
        rootClasses.push(classes.active);
    }
    //onClick={()=>setVisible(false)}
    return (
        <div className={rootClasses.join(' ')} >
            <div className={classes.myModalContent}>
                {children}
            </div>
        </div>
    )
}