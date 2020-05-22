case class Message(
                    time: String,
                    plate_id: String,
                    drone_id: Option[String] = None,
                    violation_code: Option[String] = None,      // Opt car les drones envoient aussi des info regulieres
                    address: Option[String] = None,              // fourni par le CSV
                    location: Option[String] = None             // Longitude et latitude fourni par le drone simulator
                  )

/*
On a 3 types de msg:
  - Msg du CSV (plate_id, time, address, violation_code)

  - Msg du drone 1 (time, plate_id, drone_id, violation_code, location)

  - Msg du drone 2 (time, drone_id, location)

Rmq: Pour differencier drone et CSV on regarde si address est null
 */