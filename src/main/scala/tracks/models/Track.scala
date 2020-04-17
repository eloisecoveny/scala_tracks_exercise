package tracks.models

case class Track(`type`: String,
                 id: String,
                 urn: String,
                 titles: Option[TrackTitles],
                 availability: Option[TrackAvailability])

case class TrackTitles(primary: Option[String],
                       secondary: Option[String],
                       tertiary: Option[String])

case class TrackAvailability(from: Option[String],
                             to: Option[String],
                             label: Option[String])