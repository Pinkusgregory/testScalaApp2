import com.google.inject.{AbstractModule, Inject, Singleton}
import java.time.Clock

import org.squeryl.adapters.PostgreSqlAdapter
import org.squeryl.{Session, SessionFactory}

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.

 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
@Singleton
class SquerylInit @Inject() (db: play.api.db.Database) {
  SessionFactory.concreteFactory = Some{() =>
    val session = Session.create(db.getConnection(), new PostgreSqlAdapter)
    session.bindToCurrentThread
    session
  }
}

class Module extends AbstractModule {

  override def configure() = {
    // Use the system clock as the default implementation of Clock
    bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)
    // Ask Guice to create an instance of ApplicationTimer when the
    // application starts.
    bind(classOf[SquerylInit]).asEagerSingleton()
  }

}
