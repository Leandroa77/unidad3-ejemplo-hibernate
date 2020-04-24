package ar.edu.unq.unidad3.service.runner

import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration

class SessionFactoryProvider private constructor() {

    private val sessionFactory: SessionFactory?

    init {
        val env = System.getenv()
        val user = env.getOrDefault("USER", "root")
        val password = env.getOrDefault("PASSWORD", "root")
        val dataBase = env.getOrDefault("DATA_BASE", "epers_ejemplo_hibernate")
        val host = env.getOrDefault("HOST", "localhost")


        val configuration = Configuration()
        configuration.configure("hibernate.cfg.xml")
        configuration.setProperty("hibernate.connection.username", user)
        configuration.setProperty("hibernate.connection.password", password)
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://$host:3306/$dataBase")
        this.sessionFactory = configuration.buildSessionFactory()
    }

    fun createSession(): Session {
        return this.sessionFactory!!.openSession()
    }

    companion object {

        private var INSTANCE: SessionFactoryProvider? = null

        val instance: SessionFactoryProvider
            get() {
                if (INSTANCE == null) {
                    INSTANCE =
                        SessionFactoryProvider()
                }
                return INSTANCE!!
            }

        fun destroy() {
            if (INSTANCE != null && INSTANCE!!.sessionFactory != null) {
                INSTANCE!!.sessionFactory!!.close()
            }
            INSTANCE = null
        }
    }


}
