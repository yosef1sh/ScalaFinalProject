package utils

import java.lang.reflect.Type
import com.google.gson._
import org.joda.time.format.ISODateTimeFormat
import org.joda.time.{DateTime, DateTimeZone}
import reactivemongo.bson.BSONObjectID

object Serializers {

  val DATE_TIME_FORMATTER = ISODateTimeFormat.dateTime().withZone(DateTimeZone.UTC)

  lazy val gson = new GsonBuilder()
    .registerTypeHierarchyAdapter(classOf[Seq[Any]], new ListSerializer)
    .registerTypeHierarchyAdapter(classOf[Map[Any,Any]], new MapSerializer)
    .registerTypeHierarchyAdapter(classOf[Option[Any]], new OptionSerializer)
    .registerTypeAdapter(classOf[DateTime], new DateTimeSerializer)
    .registerTypeAdapter(classOf[BSONObjectID], new BSONObjectIDSerializer)
    .create()

  class BSONObjectIDSerializer extends JsonSerializer[BSONObjectID] {
    override def serialize(src: BSONObjectID, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
      import scala.collection.JavaConverters._
      context.serialize(src.stringify)
    }
  }

  class ListSerializer extends JsonSerializer[Seq[Any]] {
    override def serialize(src: Seq[Any], typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
      import scala.collection.JavaConverters._
      context.serialize(src.toList.asJava)
    }
  }

  class MapSerializer extends JsonSerializer[Map[Any,Any]] {
    override def serialize(src: Map[Any,Any], typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
      import scala.collection.JavaConverters._
      context.serialize(src.asJava)
    }
  }

  class OptionSerializer extends JsonSerializer[Option[Any]] {
    override def serialize(src: Option[Any], typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
      src match {
        case None => JsonNull.INSTANCE
        case Some(v) => context.serialize(v)
      }
    }
  }

  class DateTimeSerializer extends JsonSerializer[DateTime] {
    override def serialize(src: DateTime, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
      new JsonPrimitive(DATE_TIME_FORMATTER.print(src))
    }
  }


}
