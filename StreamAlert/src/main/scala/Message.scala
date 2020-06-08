case class Message(
                    time: String,
                    address: String,
                    plate_id: Option[String] = None,
                    drone_id: Option[Int] = None,
                    violation_code: Option[String] = None,      // Opt car les drones envoient aussi des info regulieres
                  )

/*
On a 3 types de msg:
  - Msg du CSV (plate_id, time, address, violation_code)

  - Msg du drone 1 (time, plate_id, drone_id, violation_code, address)

  - Msg du drone 2 (time, drone_id, address)

Rmq: Pour differencier drone et CSV on regarde si address est null
 */