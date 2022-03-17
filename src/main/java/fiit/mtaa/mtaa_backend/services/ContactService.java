package fiit.mtaa.mtaa_backend.services;

import fiit.mtaa.mtaa_backend.artifacts_data.HibernateUtil;
import fiit.mtaa.mtaa_backend.models.Contact;
import fiit.mtaa.mtaa_backend.models.User;
import fiit.mtaa.mtaa_backend.repositories.ContactRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public Contact getContactById(Long id) {
        return contactRepository.findById(id).get();
    }

    public long contactExists(Contact contact) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Contact> query;
        if (contact.getPhone() != null) {
            query = session.createQuery("SELECT c FROM Contact c WHERE c.address=:address and c.phone=:phone", Contact.class);
            query.setParameter("phone", contact.getPhone());
        } else {
            query = session.createQuery("SELECT c FROM Contact c WHERE c.address=:address and c.phone is null", Contact.class);
        }
        query.setParameter("address", contact.getAddress());
        return query.stream().count();
    }

    public Contact getContact(Contact contact) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Contact> query = session.createQuery("SELECT c FROM Contact c WHERE c.address=:address and c.phone=:phone", Contact.class);
        query.setParameter("address", contact.getAddress());
        query.setParameter("phone", contact.getPhone());
        return query.getSingleResult();
    }

    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
}
