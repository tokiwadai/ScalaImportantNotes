package sample.stream.akka.io.customFlow

import akka.stream.{Attributes, FlowShape, Inlet, Outlet}
import akka.stream.stage.{GraphStage, GraphStageLogic, InHandler, OutHandler}
import scala.collection.immutable.Seq

/*
http://blog.kunicki.org/blog/2016/07/20/implementing-a-custom-akka-streams-graph-stage/
 */
final class AccumulateWhileUnchanged[E, P](propertyExtractor: E => P)
  extends GraphStage[FlowShape[E, Seq[E]]] {

  val in: Inlet[E] = Inlet[E]("AccumulateWhileUnchanged.in")
  val out: Outlet[Seq[E]] = Outlet[Seq[E]]("AccumulateWhileUnchanged.out")

  override def shape = FlowShape(in, out)

  override def createLogic(inheritedAttributes: Attributes) = new GraphStageLogic(shape) {
    private var currentState: Option[P] = None
    private val buffer = Vector.newBuilder[E]

    setHandlers(in, out, new InHandler with OutHandler {
      override def onPush(): Unit = {
        val nextElement = grab(in)
        val nextState = propertyExtractor(nextElement)

        if (currentState.isEmpty || currentState.contains(nextState)) {
          buffer += nextElement
          pull(in)
        } else {
          val result = buffer.result()
          buffer.clear()
          buffer += nextElement
          push(out, result)
        }
        currentState = Some(nextState)
      }

      override def onPull(): Unit = {
        pull(in)
      }

      override def onUpstreamFinish(): Unit = {
        val result = buffer.result()
        if (result.nonEmpty) {
          emit(out, result)
        }
        completeStage()
      }
    })

    override def postStop(): Unit = {
      buffer.clear()
    }
  }
}
