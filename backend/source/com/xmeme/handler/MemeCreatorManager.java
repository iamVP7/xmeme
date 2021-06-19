package com.xmeme.handler;

import com.xmeme.pojo.MemeCreator;
import com.xmeme.utils.HibernateUtil;
import com.xmeme.utils.IOCommonUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.List;


import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class MemeCreatorManager {
    /**
     * @param memeCreatorName is the creator name to search
     * @return MemeCreator Object
     */
    public MemeCreator getCreator(MemeCreator searchCreator) {
        Transaction tx = null;
        MemeCreator memeCreator = null;
        try (Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<MemeCreator> query = builder.createQuery(MemeCreator.class);
            Root<MemeCreator> root = query.from(MemeCreator.class);
            if(IOCommonUtil.isValidString(searchCreator.getOwnerName())) {
                query.select(root).where(builder.equal(root.get("owner_name"), searchCreator.getOwnerName())); // NO I18N
            }else if(IOCommonUtil.isValidLong(searchCreator.getOwnerID())){
                query.select(root).where(builder.equal(root.get("owner_id"), searchCreator.getOwnerID())); // NO I18N
            }
            TypedQuery<MemeCreator> querys = session.createQuery(query);
            List<MemeCreator> allMemeCreators = querys.getResultList();
            if (IOCommonUtil.isValidList(allMemeCreators)) {
                memeCreator = allMemeCreators.get(0);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return memeCreator;
    }

    /**
     * @param memeCreatorName is the creator name to add
     * @return MemeCreator Object
     */
    public MemeCreator addMemeCreator(String memeCreatorName) {
        Session session = HibernateUtil.getSession();
        org.hibernate.Transaction tr = session.beginTransaction();
        MemeCreator memeCreator = new MemeCreator(memeCreatorName);
        session.save(memeCreator);
        tr.commit();
        if (session != null) {
            session.close();
        }
        return memeCreator;
    }
}
