package com.bookstore.forreal.Controller;

import java.util.Date;
import java.util.List;

import com.bookstore.forreal.Model.Entities.Author;
import com.bookstore.forreal.Model.Entities.Book;
import com.bookstore.forreal.Model.Entities.Genre;
import com.bookstore.forreal.Model.Entities.Language;
import com.bookstore.forreal.Model.Services.Services;



public class bindingAction<Source,Sub,Service extends Services,Subservice extends Services>
{
    public List getmethod(Object source, Object sub)
    {
        if (source instanceof Book)
        {
            if (sub instanceof Author) return ((Book)source).getAuthors();
            if (sub instanceof Genre) return ((Book)source).getGenres();
        }
        if (source instanceof Author) return ((Author)source).getBooks();
        if (source instanceof Genre) return ((Genre)source).getBooks();
        return null;
    }

    public void setModifieddate(Object object)
    {
        if (object instanceof Book) ((Book)object).setModifiledDate(new Date());
        if (object instanceof Author) ((Author)object).setModifiledDate(new Date());
        if (object instanceof Genre) ((Genre)object).setModifiledDate(new Date());
        if (object instanceof Language) ((Language)object).setModifiledDate(new Date());
    }

    public void addToSource(Service service,Subservice subservice,Source source,String newone,List<Source> list)
    {   
        Sub newOne = (Sub)subservice.findByName(newone);
        List<Sub> subs = (List<Sub>) getmethod(source, newOne);
        if (newOne != null)
        {    
            if (!list.contains(newOne)) 
            {   
                subs.add(newOne);
                ((List<Source>)getmethod(newOne, source)).add(source);
                setModifieddate(newOne);
            }
            subservice.Save(newOne);
        }
    }

    public void removeFromSource(Service service,Subservice subservice,Source source,String dumbone,List<Source> list)
    {   
        Sub dumbOne = (Sub)subservice.findByName(dumbone);
        List<Sub> subs = (List<Sub>) getmethod(source, dumbOne);
        if (dumbOne != null)
        {   
            System.out.println("ok");
            subs.remove(dumbOne);
            ((List<Source>)getmethod(dumbOne, source)).remove(source);
            setModifieddate(dumbOne);
            subservice.Save(dumbOne);
        }  
    }
}